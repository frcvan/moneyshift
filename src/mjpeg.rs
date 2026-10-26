//! Camera footage from a PhotonVision MJPEG stream.
//!
//! PhotonVision exposes each camera as a `multipart/x-mixed-replace` HTTP response whose parts are
//! JPEG pictures. The bridge opens that response over a plain socket, splits the parts itself and
//! hands every picture to the recorder as an `EncodedImage`, so no external process is involved
//! and the picture can be shown both in the camera's own pane and on its pinhole in the field view.
//!
//! Only `http://` is supported, and redirects are not followed: PhotonVision's coprocessor URLs
//! are plain HTTP on the LAN, so a TLS stack would be weight the release binary does not need.

use std::{
    io::{ErrorKind, Read as _, Write as _},
    net::{SocketAddr, TcpStream, ToSocketAddrs as _},
    sync::{
        Arc,
        atomic::{AtomicBool, Ordering},
    },
    thread::JoinHandle,
    time::{Duration, Instant},
};

use anyhow::{Context as _, bail};

/// Delay before opening the camera again after a connection ends.
const RETRY_DELAY: Duration = Duration::from_secs(1);
/// Upper bound on that delay, so a camera that comes back is picked up again soon.
const MAX_RETRY_DELAY: Duration = Duration::from_secs(10);
/// Slice of the retry delay, so a stop is honored quickly.
const STOP_POLL: Duration = Duration::from_millis(100);
/// How long to wait for the camera's TCP connection to open.
const CONNECT_TIMEOUT: Duration = Duration::from_secs(3);
/// Upper bound on the response headers, which are a few hundred bytes in practice.
const MAX_HEADER: usize = 64 * 1024;
/// Upper bound on buffered body bytes.
///
/// Frames are tens of kilobytes, so this is only reached when the framing is not what the server
/// declared; the parser then falls back to looking for JPEG markers instead of buffering forever.
const MAX_BUFFER: usize = 8 * 1024 * 1024;

/// A running camera: one connection at a time, reopened for as long as the camera is declared.
pub struct CameraVideo {
    stop: Arc<AtomicBool>,
    thread: Option<JoinHandle<()>>,
}

impl CameraVideo {
    /// Records the MJPEG stream at `url` until the returned value is dropped.
    ///
    /// `on_frame` receives one complete JPEG per camera frame, in the order the camera sent them.
    /// It runs on the reader thread, so it should not block.
    pub fn spawn(
        url: String,
        on_frame: impl Fn(JpegFrame) + Send + Sync + 'static,
    ) -> anyhow::Result<Self> {
        let stop = Arc::new(AtomicBool::new(false));
        let thread_stop = Arc::clone(&stop);
        let thread = std::thread::Builder::new()
            .name(format!("camera-{url}"))
            .spawn(move || {
                // An unreachable camera is normal (a simulation may not serve one yet), so report
                // the first failure loudly and the rest quietly, with a growing retry delay.
                let mut warned = false;
                let mut delay = RETRY_DELAY;
                while !thread_stop.load(Ordering::Relaxed) {
                    let frames = match record(&url, &on_frame, &thread_stop) {
                        Ok(frames) => frames,
                        Err(_) if thread_stop.load(Ordering::Relaxed) => 0,
                        Err(error) => {
                            if warned {
                                re_log::debug!("camera stream {url} stopped: {error:#}");
                            } else {
                                re_log::warn!("camera stream {url} stopped: {error:#}");
                            }
                            0
                        }
                    };
                    if frames > 0 {
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

/// One JPEG picture, exactly the bytes the camera sent.
#[derive(Clone, Debug, PartialEq, Eq)]
pub struct JpegFrame {
    pub data: Vec<u8>,
}

/// Opens one MJPEG connection and feeds the callback until the stream ends or the reader stops.
///
/// Returns how many frames were logged, which tells the supervisor whether the camera worked.
fn record(
    url: &str,
    on_frame: &(impl Fn(JpegFrame) + Sync),
    stop: &AtomicBool,
) -> anyhow::Result<usize> {
    let (authority, path) = split_url(url)?;
    let addr = resolve(authority)?;
    let mut socket = TcpStream::connect_timeout(&addr, CONNECT_TIMEOUT)
        .with_context(|| format!("could not connect to {authority}"))?;
    // Every read is a poll rather than a blocking wait, so a camera that goes quiet cannot hold
    // the reader for longer than it takes to notice a stop.
    socket
        .set_read_timeout(Some(STOP_POLL))
        .context("could not set the camera read timeout")?;
    let _ = socket.set_write_timeout(Some(CONNECT_TIMEOUT));
    let _ = socket.set_nodelay(true);

    // HTTP/1.0 avoids keep-alive: the body has no length, it just runs until the camera stops.
    let request = format!(
        "GET {path} HTTP/1.0\r\nHost: {authority}\r\nAccept: multipart/x-mixed-replace\r\n\r\n"
    );
    socket
        .write_all(request.as_bytes())
        .and_then(|()| socket.flush())
        .with_context(|| format!("could not request the camera stream at {url}"))?;

    let (boundary, body) =
        read_headers(&mut socket, stop).with_context(|| format!("no MJPEG stream at {url}"))?;
    let mut parser = FrameParser::new(boundary);
    parser.push(&body);

    let mut frames = 0;
    let mut buffer = [0u8; 64 * 1024];
    while !stop.load(Ordering::Relaxed) {
        while let Some(jpeg) = parser.next_frame() {
            on_frame(JpegFrame { data: jpeg });
            frames += 1;
            if stop.load(Ordering::Relaxed) {
                return Ok(frames);
            }
        }
        match socket.read(&mut buffer) {
            // The camera closed the stream; the supervisor decides whether to reconnect.
            Ok(0) => break,
            Ok(read) => parser.push(&buffer[..read]),
            Err(error) if error.kind() == ErrorKind::Interrupted => {}
            Err(error) if matches!(error.kind(), ErrorKind::WouldBlock | ErrorKind::TimedOut) => {}
            Err(error) => {
                if frames == 0 {
                    return Err(error).with_context(|| format!("could not read {url}"));
                }
                break;
            }
        }
    }
    Ok(frames)
}

/// Splits `url` into the `host:port` to connect to and the request path.
fn split_url(url: &str) -> anyhow::Result<(&str, &str)> {
    let Some(rest) = url.strip_prefix("http://") else {
        bail!(
            "camera stream {url} is not a plain http:// url; TLS and redirects are not supported"
        );
    };
    let (authority, path) = match rest.find('/') {
        Some(index) => (&rest[..index], &rest[index..]),
        None => (rest, "/"),
    };
    if authority.is_empty() {
        bail!("camera stream {url} has no host");
    }
    Ok((authority, path))
}

fn resolve(authority: &str) -> anyhow::Result<SocketAddr> {
    authority
        .to_socket_addrs()
        .with_context(|| format!("could not resolve {authority} (a port is required)"))?
        .next()
        .with_context(|| format!("{authority} did not resolve to an address"))
}

/// Reads the response headers, returning the declared boundary and any body bytes already read.
fn read_headers(
    socket: &mut TcpStream,
    stop: &AtomicBool,
) -> anyhow::Result<(Option<Vec<u8>>, Vec<u8>)> {
    let mut buffer = Vec::new();
    let mut chunk = [0u8; 4096];
    loop {
        if stop.load(Ordering::Relaxed) {
            bail!("stopped while waiting for the stream");
        }
        match socket.read(&mut chunk) {
            Ok(0) => bail!("the stream closed before its headers"),
            Ok(read) => {
                buffer.extend_from_slice(&chunk[..read]);
                if let Some(end) = find(&buffer, b"\r\n\r\n") {
                    let headers = String::from_utf8_lossy(&buffer[..end]);
                    let status = headers.lines().next().unwrap_or_default().trim();
                    if !status.starts_with("HTTP/") || !status.contains(" 200") {
                        bail!("the camera answered \"{status}\"");
                    }
                    return Ok((boundary_from(&headers), buffer[end + 4..].to_vec()));
                }
                if buffer.len() > MAX_HEADER {
                    bail!("the camera sent more than {MAX_HEADER} bytes of headers");
                }
            }
            Err(error) if error.kind() == ErrorKind::Interrupted => {}
            Err(error) if matches!(error.kind(), ErrorKind::WouldBlock | ErrorKind::TimedOut) => {}
            Err(error) => return Err(error.into()),
        }
    }
}

/// The `boundary=` parameter of an MJPEG response, without its quotes.
fn boundary_from(headers: &str) -> Option<Vec<u8>> {
    for line in headers.lines() {
        let Some((name, value)) = line.split_once(':') else {
            continue;
        };
        if !name.trim().eq_ignore_ascii_case("content-type") {
            continue;
        }
        let lowercase = value.to_ascii_lowercase();
        let Some(index) = lowercase.find("boundary=") else {
            continue;
        };
        let boundary = &value[index + "boundary=".len()..];
        let boundary = boundary.split(';').next().unwrap_or(boundary).trim();
        let boundary = boundary.trim_matches('"');
        if boundary.is_empty() {
            return None;
        }
        return Some(boundary.as_bytes().to_vec());
    }
    None
}

/// Splits an MJPEG body into JPEG pictures.
#[derive(Debug)]
struct FrameParser {
    /// The part delimiter including its leading `--`, or `None` when the server declared no
    /// boundary; the frames are then delimited by JPEG markers instead.
    delimiter: Option<Vec<u8>>,
    buffer: Vec<u8>,
}

impl FrameParser {
    fn new(boundary: Option<Vec<u8>>) -> Self {
        // Each part is delimited by `--` plus the declared boundary. Some servers put the dashes
        // in the declaration itself, which would otherwise never match.
        let delimiter = boundary.map(|boundary| {
            let boundary = boundary.strip_prefix(b"--").unwrap_or(&boundary);
            let mut delimiter = b"--".to_vec();
            delimiter.extend_from_slice(boundary);
            delimiter
        });
        Self {
            delimiter,
            buffer: Vec::new(),
        }
    }

    fn push(&mut self, bytes: &[u8]) {
        self.buffer.extend_from_slice(bytes);
        if self.buffer.len() > MAX_BUFFER {
            // The server is not framing the way it said it would. Searching for JPEG markers
            // still finds the pictures, whereas buffering forever would not.
            self.delimiter = None;
            self.buffer.drain(..self.buffer.len() - MAX_BUFFER / 2);
        }
    }

    /// Returns the next complete picture, if one has arrived.
    fn next_frame(&mut self) -> Option<Vec<u8>> {
        loop {
            let Some(delimiter) = self.delimiter.clone() else {
                return self.next_scanned_frame();
            };
            // Anything before the first delimiter is the response's preamble.
            let start = find(&self.buffer, &delimiter)?;
            self.buffer.drain(..start);
            let after = delimiter.len();
            // A part normally runs up to the next delimiter, which also ends the picture.
            if let Some(next) = find(&self.buffer[after..], &delimiter) {
                let part = self.buffer[after..after + next].to_vec();
                self.buffer.drain(..after + next);
                // A part without a picture (an empty one, or a preamble that merely mentions the
                // boundary) is skipped so the frames after it are not lost.
                if let Some((start, end)) = jpeg_range(&part) {
                    return Some(part[start..end].to_vec());
                }
                continue;
            }
            // A camera may pause before the delimiter, or send a single part and stop. A picture
            // whose end marker has arrived is complete either way, so do not wait for a part that
            // may never be closed; the trailing CRLF before the next delimiter is skipped then.
            let (start, end) = jpeg_range(&self.buffer[after..])?;
            let jpeg = self.buffer[after + start..after + end].to_vec();
            self.buffer.drain(..after + end);
            return Some(jpeg);
        }
    }

    /// Reads the next picture by its `FF D8 … FF D9` markers, for a server that sends no
    /// boundary at all.
    fn next_scanned_frame(&mut self) -> Option<Vec<u8>> {
        let Some(start) = find(&self.buffer, &[0xFF, 0xD8]) else {
            // Nothing buffered is the start of a picture. Keep a trailing `FF`, which may be the
            // first half of a marker split across reads.
            let keep = usize::from(self.buffer.last() == Some(&0xFF));
            let end = self.buffer.len() - keep;
            self.buffer.drain(..end);
            return None;
        };
        self.buffer.drain(..start);
        let (_, end) = jpeg_range(&self.buffer)?;
        let frame = self.buffer[..end].to_vec();
        self.buffer.drain(..end);
        Some(frame)
    }
}

/// The byte range of the JPEG in one part: from `FF D8` through its `FF D9`.
///
/// A part may carry its own headers (`Content-Type: image/jpeg`, `Content-Length: ...`) before the
/// picture and a trailing CRLF after it; the marker range drops both, and a part with no complete
/// picture is rejected.
fn jpeg_range(part: &[u8]) -> Option<(usize, usize)> {
    let start = find(part, &[0xFF, 0xD8])?;
    let end = find(&part[start + 2..], &[0xFF, 0xD9])? + start + 4;
    Some((start, end))
}

fn find(haystack: &[u8], needle: &[u8]) -> Option<usize> {
    haystack
        .windows(needle.len())
        .position(|window| window == needle)
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
    use std::net::{TcpListener, TcpStream};
    use std::sync::mpsc;
    use std::time::{Duration, Instant};

    /// A real JPEG, as PhotonVision sends it.
    const JPEG: &[u8] = include_bytes!("../test_data/camera_frame.jpg");
    const BOUNDARY: &str = "myboundary";

    fn response_headers() -> Vec<u8> {
        format!(
            "HTTP/1.0 200 OK\r\nContent-Type: multipart/x-mixed-replace; \
             boundary={BOUNDARY}\r\n\r\n"
        )
        .into_bytes()
    }

    /// One MJPEG part, with the headers PhotonVision puts in front of each picture.
    fn part(jpeg: &[u8]) -> Vec<u8> {
        let mut bytes = format!(
            "--{BOUNDARY}\r\nContent-Type: image/jpeg\r\nContent-Length: {}\r\n\r\n",
            jpeg.len()
        )
        .into_bytes();
        bytes.extend_from_slice(jpeg);
        bytes.extend_from_slice(b"\r\n");
        bytes
    }

    /// Accepts one connection, reads its request and returns the socket once it is ready.
    fn accept(listener: &TcpListener) -> TcpStream {
        let (mut socket, _) = listener.accept().unwrap();
        let mut request = [0u8; 1024];
        let _ = socket.read(&mut request);
        socket.write_all(&response_headers()).unwrap();
        socket.flush().unwrap();
        socket
    }

    #[test]
    fn parses_one_jpeg_per_part() {
        let listener = TcpListener::bind("127.0.0.1:0").unwrap();
        let port = listener.local_addr().unwrap().port();
        let server = std::thread::spawn(move || {
            let mut socket = accept(&listener);
            socket.write_all(&part(JPEG)).unwrap();
            socket.write_all(&part(JPEG)).unwrap();
            socket.flush().unwrap();
            // A camera keeps the connection open between frames.
            std::thread::sleep(Duration::from_secs(3));
        });

        let (frame_tx, frames) = mpsc::channel();
        let video = CameraVideo::spawn(
            format!("http://127.0.0.1:{port}/stream.mjpg"),
            move |frame| {
                let _ = frame_tx.send(frame);
            },
        )
        .unwrap();

        for index in 0..2 {
            let frame = frames
                .recv_timeout(Duration::from_secs(5))
                .unwrap_or_else(|_| panic!("frame {index} never arrived"));
            assert_eq!(frame.data, JPEG, "a frame is the JPEG the camera sent");
            assert!(
                frame.data.starts_with(&[0xFF, 0xD8]),
                "a frame starts at SOI"
            );
            assert!(frame.data.ends_with(&[0xFF, 0xD9]), "a frame ends at EOI");
        }
        drop(video);
        server.join().unwrap();
    }

    #[test]
    fn survives_a_part_split_across_reads() {
        let listener = TcpListener::bind("127.0.0.1:0").unwrap();
        let port = listener.local_addr().unwrap().port();
        let server = std::thread::spawn(move || {
            let mut socket = accept(&listener);
            let body = part(JPEG);
            let (head, tail) = body.split_at(body.len() / 2);
            socket.write_all(head).unwrap();
            socket.flush().unwrap();
            std::thread::sleep(Duration::from_millis(100));
            socket.write_all(tail).unwrap();
            socket.flush().unwrap();
            std::thread::sleep(Duration::from_secs(3));
        });

        let (frame_tx, frames) = mpsc::channel();
        let video = CameraVideo::spawn(
            format!("http://127.0.0.1:{port}/stream.mjpg"),
            move |frame| {
                let _ = frame_tx.send(frame);
            },
        )
        .unwrap();

        let frame = frames
            .recv_timeout(Duration::from_secs(5))
            .expect("a part split across reads is still one frame");
        assert_eq!(frame.data, JPEG);
        drop(video);
        server.join().unwrap();
    }

    #[test]
    fn stops_promptly() {
        let listener = TcpListener::bind("127.0.0.1:0").unwrap();
        let port = listener.local_addr().unwrap().port();
        let server = std::thread::spawn(move || {
            let mut socket = accept(&listener);
            socket.write_all(&part(JPEG)).unwrap();
            socket.flush().unwrap();
            // The camera goes quiet but the socket stays open.
            std::thread::sleep(Duration::from_secs(3));
        });

        let (frame_tx, frames) = mpsc::channel();
        let video = CameraVideo::spawn(
            format!("http://127.0.0.1:{port}/stream.mjpg"),
            move |frame| {
                let _ = frame_tx.send(frame);
            },
        )
        .unwrap();
        frames
            .recv_timeout(Duration::from_secs(5))
            .expect("the camera logged a frame before it went quiet");

        let dropped = Instant::now();
        drop(video);
        assert!(
            dropped.elapsed() < Duration::from_secs(1),
            "a camera reader must stop promptly, took {:?}",
            dropped.elapsed()
        );
        server.join().unwrap();
    }

    #[test]
    fn reconnects_after_the_server_closes() {
        let listener = TcpListener::bind("127.0.0.1:0").unwrap();
        let port = listener.local_addr().unwrap().port();
        let server = std::thread::spawn(move || {
            for index in 0..2 {
                let mut socket = accept(&listener);
                socket.write_all(&part(JPEG)).unwrap();
                socket.flush().unwrap();
                // Closing the first connection is what makes the bridge reconnect.
                if index == 0 {
                    drop(socket);
                } else {
                    std::thread::sleep(Duration::from_secs(2));
                }
            }
        });

        let (frame_tx, frames) = mpsc::channel();
        let video = CameraVideo::spawn(
            format!("http://127.0.0.1:{port}/stream.mjpg"),
            move |frame| {
                let _ = frame_tx.send(frame);
            },
        )
        .unwrap();

        for index in 0..2 {
            let frame = frames
                .recv_timeout(Duration::from_secs(10))
                .unwrap_or_else(|_| panic!("connection {} never delivered a frame", index + 1));
            assert_eq!(frame.data, JPEG, "both connections carry the same picture");
        }
        drop(video);
        server.join().unwrap();
    }
}
