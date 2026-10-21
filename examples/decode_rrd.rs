//! Decodes an `.rrd` file with Rerun's own decoder.
//!
//! This is the cross-language check for the Java SDK: whatever `moneyshift` writes must be
//! readable by the same code path the viewer uses.
//!
//! Usage: `cargo run --example decode_rrd -- <path.rrd>`

use re_log_encoding::rrd::Decoder;
use re_log_types::LogMsg;

fn main() -> anyhow::Result<()> {
    let path = std::env::args().nth(1).unwrap_or_else(|| {
        eprintln!("usage: cargo run --example decode_rrd -- <path.rrd>");
        std::process::exit(2);
    });

    let bytes = std::fs::read(&path)?;
    println!("read {} bytes from {path}", bytes.len());

    let mut decoder: Decoder<LogMsg> = Decoder::new();
    decoder.push_byte_chunk(bytes);

    let mut messages = 0;
    let mut chunks = 0;
    let mut rows = 0;
    while let Some(msg) = decoder.try_read()? {
        messages += 1;
        match &msg {
            LogMsg::SetStoreInfo(info) => {
                println!(
                    "SetStoreInfo app={:?} recording={:?}",
                    info.info.store_id.application_id(),
                    info.info.store_id.recording_id(),
                );
            }
            LogMsg::ArrowMsg(_, arrow) => {
                chunks += 1;
                let batch = &arrow.batch;
                rows += batch.num_rows();
                println!(
                    "ArrowMsg chunk_id={} rows={} columns={}",
                    arrow.chunk_id,
                    batch.num_rows(),
                    batch.num_columns(),
                );
                for field in batch.schema().fields() {
                    let component = field
                        .metadata()
                        .get("rerun:component")
                        .map_or("", String::as_str);
                    println!(
                        "  column {:<40} component={:<40} type={:?}",
                        field.name(),
                        component,
                        field.data_type(),
                    );
                }
            }
            LogMsg::BlueprintActivationCommand(_) => {
                println!("BlueprintActivationCommand");
            }
        }
    }

    println!("decoded {messages} messages, {chunks} chunks, {rows} rows");
    assert!(messages > 0, "the stream must contain messages");
    Ok(())
}
