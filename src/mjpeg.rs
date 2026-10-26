//! Camera footage from an MJPEG stream, transcoded by `ffmpeg`.
//!
//! PhotonVision exposes each camera as a `multipart/x-mixed-replace` HTTP stream, which Rerun has
//! no codec for. `ffmpeg` opens that stream itself (its `mpjpeg` demuxer) and writes H.264 to
//! stdout, one access unit per frame; Rerun then plays it as a `VideoStream`. Re-encoding is what
//! makes a live feed smooth, because every frame after the first is a delta rather than a whole
//! JPEG that the viewer reloads and re-uploads.
//!
//! Nothing here parses the MJPEG container. `ffmpeg` also reconnects to the camera on its own; the
//! supervisor restarts the process if it exits (a rebooting coprocessor, say).

use std::{
    process::{Command, Stdio},
    sync::{
        Arc, OnceLock,
        atomic::{AtomicBool, Ordering},
    },
    thread::JoinHandle,
    time::{Duration, Instant},
};

use anyhow::Context as _;

/// Delay before restarting `ffmpeg` after it exits.
const RETRY_DELAY: Duration = Duration::from_secs(1);
/// Upper bound on that delay, so a camera that comes back is picked up again soon.
const MAX_RETRY_DELAY: Duration = Duration::from_secs(10);
/// Slice of the retry delay, so a stop is honored quickly.
const STOP_POLL: Duration = Duration::from_millis(100);
/// How many frames between keyframes in the H.264 stream, so a viewer can join late.
///
/// This was tried at 10 frames to cut the viewer's catch-up work at the live edge, and the
/// buffering overlay only became more frequent: more IDRs give its player more places to re-anchor,
/// so the lever is a shorter keyframe interval. Keep 30.
const KEYFRAME_INTERVAL: &str = "30";

/// A running camera: one `ffmpeg` per connection, restarted for as long as the camera is declared.
pub struct CameraVideo {
    stop: Arc<AtomicBool>,
    thread: Option<JoinHandle<()>>,
}

impl CameraVideo {
    /// Records the MJPEG stream at `url` until the returned value is dropped.
    ///
    /// `on_sample` receives one H.264 access unit per camera frame, in presentation order, for a
    /// Rerun `VideoStream`. It runs on a reader thread of `ffmpeg`'s output, so it should not
    /// block.
    pub fn spawn(
        url: String,
        on_sample: impl Fn(H264Sample) + Send + Sync + 'static,
    ) -> anyhow::Result<Self> {
        let stop = Arc::new(AtomicBool::new(false));
        let thread_stop = Arc::clone(&stop);
        let thread = std::thread::Builder::new()
            .name(format!("camera-{url}"))
            .spawn(move || {
                // An unreachable camera is normal (a simulated robot serves no MJPEG), so report
                // the first failure loudly and the rest quietly, with a growing retry delay.
                let mut warned = false;
                let mut delay = RETRY_DELAY;
                while !thread_stop.load(Ordering::Relaxed) {
                    let samples = match record(&url, &on_sample, &thread_stop) {
                        Ok(samples) => samples,
                        Err(error) => {
                            if warned {
                                re_log::debug!("camera stream {url} stopped: {error:#}");
                            } else {
                                re_log::warn!("camera stream {url} stopped: {error:#}");
                            }
                            0
                        }
                    };
                    if samples > 0 {
                        warned = false;
                        delay = RETRY_DELAY;
                    } else {
                        warned = true;
                        delay = (delay * 2).min(MAX_RETRY_DELAY);
                    }
                    sleep_unless_stopped(delay, &thread_stop);
                }
            })
            .context("could not start the camera thread")?;
        Ok(Self {
            stop,
            thread: Some(thread),
        })
    }
}

impl Drop for CameraVideo {
    fn drop(&mut self) {
        self.stop.store(true, Ordering::Relaxed);
        if let Some(thread) = self.thread.take() {
            let _ = thread.join();
        }
    }
}

/// Runs one `ffmpeg` until it exits, feeding the callback from its H.264 output.
///
/// Returns how many samples were logged, which tells the supervisor whether the camera worked.
fn record(
    url: &str,
    on_sample: &(impl Fn(H264Sample) + Sync),
    stop: &AtomicBool,
) -> anyhow::Result<usize> {
    let mut child = Command::new("ffmpeg")
        .args(["-hide_banner", "-loglevel", "quiet"])
        // The camera is MJPEG over HTTP. Without the probe limits ffmpeg analyzes seconds of
        // input before its first output packet, which looks like a camera that never starts;
        // the reconnect options survive a coprocessor that reboots mid-match.
        .args(["-f", "mpjpeg", "-probesize", "32", "-analyzeduration", "0"])
        .args(["-reconnect", "1", "-reconnect_streamed", "1"])
        .args(["-reconnect_delay_max", "5", "-i"])
        .arg(url)
        // One H.264 access unit per frame, with SPS/PPS repeated before every keyframe so a
        // viewer that joins mid-stream can decode.
        .args([
            "-map",
            "0:v",
            "-an",
            "-c:v",
            "libx264",
            "-preset",
            "ultrafast",
        ])
        .args(["-tune", "zerolatency", "-pix_fmt", "yuv420p", "-g"])
        .args([KEYFRAME_INTERVAL, "-x264-params", "aud=1:repeat-headers=1"])
        .args(["-flush_packets", "1", "-f", "h264", "pipe:1"])
        .stdin(Stdio::null())
        .stdout(Stdio::piped())
        .stderr(Stdio::null())
        .spawn()
        .context("could not start ffmpeg")?;

    let stdout = child.stdout.take().expect("ffmpeg stdout is piped");
    let samples = read_video(stdout, on_sample, stop);

    let _ = child.kill();
    let _ = child.wait();
    Ok(samples)
}

/// Reads H.264 access units from `stdout` until the stream ends or the reader is stopped.
fn read_video(
    mut stdout: impl std::io::Read,
    on_sample: &(impl Fn(H264Sample) + Sync),
    stop: &AtomicBool,
) -> usize {
    let mut splitter = AnnexBSplitter::default();
    let mut buffer = [0u8; 16 * 1024];
    let mut samples = 0;
    while !stop.load(Ordering::Relaxed) {
        match stdout.read(&mut buffer) {
            Ok(0) => break,
            Ok(read) => {
                for sample in splitter.push(&buffer[..read]) {
                    on_sample(sample);
                    samples += 1;
                }
            }
            Err(error) if error.kind() == std::io::ErrorKind::Interrupted => {}
            Err(_) => break,
        }
    }
    samples
}

/// Whether an `ffmpeg` binary is available to record camera streams.
///
/// Checked once: a missing `ffmpeg` must not turn into a restart loop per camera.
pub fn ffmpeg_available() -> bool {
    static AVAILABLE: OnceLock<bool> = OnceLock::new();
    *AVAILABLE.get_or_init(|| {
        Command::new("ffmpeg")
            .arg("-version")
            .stdin(Stdio::null())
            .stdout(Stdio::null())
            .stderr(Stdio::null())
            .status()
            .is_ok_and(|status| status.success())
    })
}

/// One H.264 access unit: exactly one encoded frame, in Annex-B form.
#[derive(Clone, Debug, PartialEq, Eq)]
pub struct H264Sample {
    pub data: Vec<u8>,
    /// A frame a decoder can start at (an IDR slice).
    pub keyframe: bool,
}

/// Splits an Annex-B H.264 byte stream into access units.
///
/// The stream is encoded with access-unit delimiters, so every frame starts with a NAL of type 9;
/// nothing here needs to understand slices. Bytes that do not form a complete unit yet stay
/// buffered, so reads may end anywhere.
#[derive(Debug, Default)]
pub struct AnnexBSplitter {
    buffer: Vec<u8>,
}

impl AnnexBSplitter {
    /// Appends bytes and returns every access unit that is now complete.
    pub fn push(&mut self, bytes: &[u8]) -> Vec<H264Sample> {
        self.buffer.extend_from_slice(bytes);
        let mut samples = Vec::new();
        loop {
            let starts = self.access_unit_starts();
            if starts.len() < 2 {
                break;
            }
            let unit = self.buffer.drain(..starts[1]).collect::<Vec<_>>();
            if let Some(sample) = make_sample(&unit) {
                samples.push(sample);
            }
        }
        samples
    }

    /// Returns the last buffered access unit, if any, when the stream ends.
    pub fn finish(&mut self) -> Option<H264Sample> {
        let unit = std::mem::take(&mut self.buffer);
        make_sample(&unit)
    }

    /// Byte offsets of every access-unit delimiter, including a preceding start-code zero.
    fn access_unit_starts(&self) -> Vec<usize> {
        let bytes = &self.buffer;
        let mut starts = Vec::new();
        let mut index = 0;
        while index + 3 < bytes.len() {
            if bytes[index] == 0 && bytes[index + 1] == 0 && bytes[index + 2] == 1 {
                if bytes[index + 3] & 0x1F == 9 {
                    starts.push(if index > 0 && bytes[index - 1] == 0 {
                        index - 1
                    } else {
                        index
                    });
                }
                index += 3;
            } else {
                index += 1;
            }
        }
        starts
    }
}

fn make_sample(unit: &[u8]) -> Option<H264Sample> {
    // Trailing zeros are start-code padding that belongs to the next unit.
    let mut end = unit.len();
    while end > 0 && unit[end - 1] == 0 {
        end -= 1;
    }
    let data = &unit[..end];
    if data.is_empty() {
        return None;
    }
    Some(H264Sample {
        data: data.to_vec(),
        keyframe: has_idr(data),
    })
}

/// Whether an access unit contains an IDR slice (NAL type 5).
fn has_idr(bytes: &[u8]) -> bool {
    let mut index = 0;
    while index + 3 < bytes.len() {
        if bytes[index] == 0 && bytes[index + 1] == 0 && bytes[index + 2] == 1 {
            if bytes[index + 3] & 0x1F == 5 {
                return true;
            }
            index += 3;
        } else {
            index += 1;
        }
    }
    false
}

fn sleep_unless_stopped(duration: Duration, stop: &AtomicBool) {
    let deadline = Instant::now() + duration;
    while Instant::now() < deadline {
        if stop.load(Ordering::Relaxed) {
            return;
        }
        std::thread::sleep(STOP_POLL);
    }
}

#[cfg(test)]
mod tests {
    use super::CameraVideo;
    use std::io::{Read as _, Write as _};
    use std::net::TcpListener;
    use std::sync::mpsc;
    use std::time::{Duration, Instant};

    /// A real JPEG: ffmpeg's MJPEG demuxer reads its header to learn the stream's dimensions.
    const JPEG: &[u8] = include_bytes!("../test_data/camera_frame.jpg");

    /// An access unit delimiter, a slice type, and a payload, in Annex-B form.
    fn nal(start_code: &[u8], header: u8, payload: &[u8]) -> Vec<u8> {
        [start_code, &[header][..], payload].concat()
    }

    #[test]
    fn splits_annex_b_access_units_on_delimiters() {
        let first = [
            nal(&[0, 0, 0, 1], 0x09, &[0xF0]),
            nal(&[0, 0, 0, 1], 0x67, &[0x42, 0x00, 0x1E]),
            nal(&[0, 0, 0, 1], 0x68, &[0xCE, 0x0F]),
            nal(&[0, 0, 0, 1], 0x65, &[0x88, 0x84]),
        ]
        .concat();
        let second = [
            nal(&[0, 0, 1], 0x09, &[0xF0]),
            nal(&[0, 0, 1], 0x41, &[0x9A, 0x02]),
        ]
        .concat();

        let mut splitter = super::AnnexBSplitter::default();
        assert!(splitter.push(&first).is_empty());
        let samples = splitter.push(&second);
        assert_eq!(samples.len(), 1);
        assert_eq!(samples[0].data, first);
        assert!(samples[0].keyframe, "the first frame is an IDR");

        let last = splitter.finish().unwrap();
        assert_eq!(last.data, second);
        assert!(!last.keyframe);
    }

    #[test]
    fn buffers_access_units_split_across_reads() {
        let stream = [
            nal(&[0, 0, 0, 1], 0x09, &[0xF0]),
            nal(&[0, 0, 0, 1], 0x65, &[0x88, 0x84]),
            nal(&[0, 0, 0, 1], 0x09, &[0xF0]),
            nal(&[0, 0, 0, 1], 0x41, &[0x9A]),
        ]
        .concat();
        let mut splitter = super::AnnexBSplitter::default();
        let mut samples = Vec::new();
        for byte in stream {
            samples.extend(splitter.push(&[byte]));
        }
        samples.extend(splitter.finish());
        assert_eq!(samples.len(), 2);
        assert!(samples[0].keyframe);
        assert!(!samples[1].keyframe);
    }

    #[test]
    fn reads_a_live_stream_and_stops() {
        if !super::ffmpeg_available() {
            eprintln!("ffmpeg is unavailable; skipping the camera stream test");
            return;
        }

        let listener = TcpListener::bind("127.0.0.1:0").unwrap();
        let port = listener.local_addr().unwrap().port();
        let server = std::thread::spawn(move || {
            let (mut socket, _) = listener.accept().unwrap();
            let mut request = [0u8; 1024];
            let _ = socket.read(&mut request);
            socket
                .write_all(
                    b"HTTP/1.0 200 OK\r\nContent-Type: multipart/x-mixed-replace; \
                      boundary=--myboundary\r\n\r\n",
                )
                .unwrap();
            // A camera keeps pushing frames, like PhotonVision does.
            for _ in 0..90 {
                if socket
                    .write_all(b"--myboundary\r\nContent-Type: image/jpeg\r\n\r\n")
                    .is_err()
                {
                    return;
                }
                if socket.write_all(JPEG).is_err() {
                    return;
                }
                let _ = socket.write_all(b"\r\n");
                let _ = socket.flush();
                std::thread::sleep(Duration::from_millis(33));
            }
            std::thread::sleep(Duration::from_secs(5));
        });

        let (sample_tx, samples) = mpsc::channel();
        let video = CameraVideo::spawn(
            format!("http://127.0.0.1:{port}/stream.mjpg"),
            move |sample| {
                let _ = sample_tx.send(sample);
            },
        )
        .unwrap();

        let sample = samples
            .recv_timeout(Duration::from_secs(30))
            .expect("ffmpeg produced an H.264 sample");
        assert!(sample.keyframe, "the stream starts with an IDR");
        assert!(
            sample.data.starts_with(&[0, 0, 1]) || sample.data.starts_with(&[0, 0, 0, 1]),
            "samples are Annex-B"
        );

        // Dropping the camera stops the readers instead of waiting for the next frame.
        let dropped = Instant::now();
        drop(video);
        assert!(
            dropped.elapsed() < Duration::from_secs(3),
            "a camera reader must stop promptly"
        );
        server.join().unwrap();
    }
}
