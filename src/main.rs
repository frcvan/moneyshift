//! `rerun-importer-moneyshift`: the moneyshift Rerun importer.
//!
//! Two modes share this binary:
//!
//! * `rerun-importer-moneyshift <path.wpilog> [...]` implements Rerun's external-importer protocol:
//!   RRD goes to stdout, diagnostics to stderr, and exit code 66 means "not my file".
//! * `rerun-importer-moneyshift --nt [...]` runs the live NetworkTables bridge.
//!
//! See [`moneyshift::cli`] for the flag reference.

use std::process::ExitCode;

use moneyshift::cli::{Command, parse, usage};

fn main() -> ExitCode {
    configure_logging();

    let command = match parse(std::env::args()) {
        Ok(command) => command,
        Err(error) => {
            eprintln!("moneyshift: {error}\n\n{}", usage());
            return ExitCode::from(2);
        }
    };

    match command {
        Command::Help => {
            print!("{}", usage());
            ExitCode::SUCCESS
        }
        Command::Version => {
            println!("rerun-importer-moneyshift {}", env!("CARGO_PKG_VERSION"));
            ExitCode::SUCCESS
        }
        Command::Import(args) => {
            if args.path.as_os_str().is_empty() {
                eprintln!("moneyshift: no log path given\n\n{}", usage());
                return ExitCode::from(2);
            }
            // The importer protocol owns stdout, so rerun's default "logs go to stdout" is not
            // an option here.
            moneyshift::importer::run_cli(&args)
        }
        Command::Live(args) => match moneyshift::live::run(&args) {
            Ok(()) => ExitCode::SUCCESS,
            Err(error) => {
                eprintln!("moneyshift: live bridge failed: {error}");
                ExitCode::FAILURE
            }
        },
    }
}

fn configure_logging() {
    // `re_log` reads `RUST_LOG`-style filters; the exact setup is best-effort in the SDK.
    re_log::setup_logging();
}
