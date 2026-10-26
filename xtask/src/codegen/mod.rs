mod java;

use anyhow::Context;
use camino::Utf8Path;
use camino::Utf8PathBuf;
use cargo_metadata::MetadataCommand;
use std::path::Path;

/// Generates the Java sources for every Rerun definition into `output_dir`.
///
/// The definitions come from `rerun_worktree` when one is given, and from the
/// `re_type_definitions` checkout otherwise.
///
/// # Errors
///
/// Returns an error when the type definitions cannot be located or read, when a definition has no
/// `NetworkTables` column layout, or when a generated file cannot be written.
pub fn gen_java(
    rerun_worktree: Option<&Utf8Path>,
    output_dir: &Utf8Path,
) -> Result<(), anyhow::Error> {
    std::fs::create_dir_all(output_dir).ok();

    let def_path = match rerun_worktree {
        Some(path) => path.into(),
        None => fetch_rerun_definitions()?,
    };

    let (report, reporter) = re_types_builder::report::init();

    let (objects, _type_registry) = re_types_builder::generate_lang_agnostic(&reporter, &def_path);

    println!("[+] Generating Java code to {output_dir}");

    java::generate(output_dir, &objects)?;
    report.finalize(false);

    Ok(())
}

/// The directory of the checked-out `re_type_definitions` package.
///
/// # Errors
///
/// Returns an error when the crate graph cannot be read or does not contain
/// `re_type_definitions` for the target platform.
pub fn fetch_rerun_definitions() -> Result<Utf8PathBuf, anyhow::Error> {
    let target = env!("TARGET");
    let manifest = Path::new(env!("CARGO_MANIFEST_DIR")).join("Cargo.toml");
    let metadata = MetadataCommand::new()
        .manifest_path(manifest)
        .other_options(["--filter-platform".into(), target.into()])
        .exec()
        .context("failed to read the cargo metadata")?;

    metadata
        .packages
        .iter()
        .find(|p| p.name == "re_type_definitions")
        .map(|p| p.manifest_path.as_ref())
        .and_then(|p: &Utf8Path| p.parent())
        .map(Into::into)
        .context("Failed to find re_type_definitions package")
}
