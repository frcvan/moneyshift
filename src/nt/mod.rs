//! NetworkTables support: the `/Rerun/` decoder and the plain-topic adapter.
//!
//! Both are native-only because `nt_client` is a tokio-based desktop client; the wasm importer
//! does not need them.

pub mod native;
pub mod protocol;

/// The fixed root namespace owned by the Java `NtRecorder`.
pub const ROOT: &str = "/Rerun";
