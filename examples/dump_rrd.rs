use re_log_encoding::rrd::Decoder;
use re_log_types::LogMsg;

fn main() {
    let contents = include_bytes!("../test_data/FRC_TBD_d225b5377c70a88d.wpilog");
    let rrd = firstrun::import_to_rrd(contents).unwrap();
    std::fs::write("/tmp/firstrun.rrd", &rrd).unwrap();
    println!("rrd len {}", rrd.len());
    let mut decoder: Decoder<LogMsg> = Decoder::new();
    decoder.push_byte_chunk(rrd);
    let mut n = 0;
    while let Some(msg) = decoder.try_read().unwrap() {
        n += 1;
        match &msg {
            LogMsg::SetStoreInfo(info) => println!("store info: {info:?}"),
            LogMsg::ArrowMsg(_, arrow) => {
                let b = &arrow.batch;
                println!("batch rows={} schema_meta={:?}", b.num_rows(), b.schema().metadata());
                for f in b.schema().fields() {
                    println!("  field {:?} {:?} nullable={} meta={:?}", f.name(), f.data_type(), f.is_nullable(), f.metadata());
                }
            }
            _ => println!("other"),
        }
        if n > 3 { break; }
    }
    println!("seen {n}");
}
