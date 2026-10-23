//! `rerun-importer-firstrun`: the FIRSTrun Rerun importer.
//!
//! Two modes share this binary:
//!
//! * `rerun-importer-firstrun <path.wpilog> [...]` implements Rerun's external-importer protocol:
//!   RRD goes to stdout, diagnostics to stderr, and exit code 66 means "not my file".
//! * `rerun-importer-firstrun --nt [...]` runs the live NetworkTables bridge.
//!
//! See [`firstrun::cli`] for the flag reference.

use std::process::ExitCode;

use firstrun::cli::{Command, parse, usage};

fn main() -> ExitCode {
    configure_logging();

    let command = match parse(std::env::args()) {
        Ok(command) => command,
        Err(error) => {
            eprintln!("firstrun: {error}\n\n{}", usage());
            return ExitCode::from(2);
        }
    };

    match command {
        Command::Help => {
            print!("{}", usage());
            ExitCode::SUCCESS
        }
        Command::Version => {
            println!("rerun-importer-firstrun {}", env!("CARGO_PKG_VERSION"));
            ExitCode::SUCCESS
        }
        Command::Import(args) => {
            if args.path.as_os_str().is_empty() {
                eprintln!("firstrun: no log path given\n\n{}", usage());
                return ExitCode::from(2);
            }
            // The importer protocol owns stdout, so rerun's default "logs go to stdout" is not
            // an option here.
            firstrun::importer::run_cli(&args)
        }
        Command::Live(args) => match firstrun::live::run(&args) {
            Ok(()) => ExitCode::SUCCESS,
            Err(error) => {
                eprintln!("firstrun: live bridge failed: {error}");
                ExitCode::FAILURE
            }
        },
    }
}

fn configure_logging() {
    // `re_log` reads `RUST_LOG`-style filters; the exact setup is best-effort in the SDK.
    re_log::setup_logging();
}
