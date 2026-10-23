//! End-to-end tests for `rerun-importer-firstrun` in external-importer mode.

use std::io::Write;
use std::process::Command;

use re_log_encoding::rrd::Decoder;
use re_log_types::LogMsg;

const BIN: &str = env!("CARGO_BIN_EXE_rerun-importer-firstrun");
const FIXTURE: &str = concat!(
    env!("CARGO_MANIFEST_DIR"),
    "/test_data/FRC_TBD_d225b5377c70a88d.wpilog"
);

fn decode(bytes: &[u8]) -> Vec<LogMsg> {
    let mut decoder: Decoder<LogMsg> = Decoder::new();
    decoder.push_byte_chunk(bytes.to_vec());
    let mut messages = Vec::new();
    while let Some(message) = decoder.try_read().expect("RRD decodes") {
        messages.push(message);
    }
    messages
}

fn chunks(messages: &[LogMsg]) -> Vec<re_chunk::Chunk> {
    messages
        .iter()
        .filter_map(|message| match message {
            LogMsg::ArrowMsg(_, arrow) => Some(re_chunk::Chunk::from_arrow_msg(arrow).unwrap()),
            _ => None,
        })
        .collect()
}

#[test]
fn imports_a_wpilog_to_pure_rrd_on_stdout() {
    let output = Command::new(BIN).arg(FIXTURE).output().unwrap();
    assert!(
        output.status.success(),
        "stderr: {}",
        String::from_utf8_lossy(&output.stderr)
    );
    assert!(
        output.stdout.starts_with(b"RRF2"),
        "stdout must be an RRD stream"
    );

    let messages = decode(&output.stdout);
    assert!(
        matches!(messages.first(), Some(LogMsg::SetStoreInfo(_))),
        "the stream must start with store info"
    );
    assert!(messages.len() > 5);
    assert!(!chunks(&messages).is_empty());
}

#[test]
fn refuses_non_wpilogs_with_exit_code_66_and_empty_stdout() {
    let mut dir = std::env::temp_dir();
    dir.push(format!("firstrun-cli-{}", std::process::id()));
    std::fs::create_dir_all(&dir).unwrap();
    let path = dir.join("not-a-log.txt");
    std::fs::File::create(&path)
        .unwrap()
        .write_all(b"definitely not a WPI log")
        .unwrap();

    let output = Command::new(BIN).arg(&path).output().unwrap();
    assert_eq!(output.status.code(), Some(66));
    assert!(output.stdout.is_empty());
    std::fs::remove_dir_all(&dir).ok();
}

#[test]
fn honors_static_and_entity_path_prefix() {
    let output = Command::new(BIN)
        .args(["--static", "--entity-path-prefix", "robot", FIXTURE])
        .output()
        .unwrap();
    assert!(
        output.status.success(),
        "stderr: {}",
        String::from_utf8_lossy(&output.stderr)
    );

    let chunks = chunks(&decode(&output.stdout));
    assert!(!chunks.is_empty());
    assert!(chunks.iter().all(re_chunk::Chunk::is_static));
    assert!(
        chunks
            .iter()
            .any(|chunk| chunk.entity_path().to_string().starts_with("/robot/"))
    );
}

#[test]
fn honors_time_flags() {
    let output = Command::new(BIN)
        .args([
            "--time_sequence",
            "frame=3",
            "--time_duration_nanos",
            "sim=1500",
            FIXTURE,
        ])
        .output()
        .unwrap();
    assert!(
        output.status.success(),
        "stderr: {}",
        String::from_utf8_lossy(&output.stderr)
    );

    let chunks = chunks(&decode(&output.stdout));
    assert!(!chunks.is_empty());
    for timeline in ["frame", "sim"] {
        assert!(
            chunks.iter().any(|chunk| chunk
                .timelines()
                .keys()
                .any(|name| name.as_str() == timeline)),
            "expected a {timeline} timeline"
        );
    }
}

#[test]
fn rejects_bad_arguments_with_exit_code_2() {
    let output = Command::new(BIN)
        .args(["--not-a-flag", FIXTURE])
        .output()
        .unwrap();
    assert_eq!(output.status.code(), Some(2));
    assert!(output.stdout.is_empty());
}
