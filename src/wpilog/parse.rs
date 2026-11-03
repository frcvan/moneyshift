use std::collections::HashSet;

use nom::{
    IResult, bytes::streaming as bstreaming, error::ErrorKind, number::streaming as nstreaming,
};

use crate::log;

/// Magic, version and extra-header length: the part of the header every reader needs.
const FIXED_HEADER_LEN: usize = 12;

/// How many consecutive records a resynchronisation candidate must produce, so that payload
/// bytes that happen to parse as a record are not mistaken for the end of the damage.
const RESYNC_CHAIN: usize = 4;

/// How far ahead of the last intact record a candidate may be, in microseconds.
const RESYNC_MAX_GAP_US: u64 = 5_000_000;

/// How far behind the last intact record a candidate may be. Records are allowed to arrive out
/// of order, but only barely, and a candidate that jumps backwards is almost always junk.
const RESYNC_MAX_BACKWARD_US: u64 = 100_000;

/// How far a damaged region is scanned for the next intact record before the rest is dropped.
const RESYNC_MAX_SCAN_BYTES: usize = 8 << 20;

struct RecordHeaderLengths(u8);
impl RecordHeaderLengths {
    /// Returns the size of the entry ID field in bytes.
    pub const fn size_entry_id(&self) -> u8 {
        (self.0 & 0b0000_0011) + 1
    }

    /// Returns the size of the payload length field in bytes.
    pub const fn size_payload_len(&self) -> u8 {
        ((self.0 & 0b0000_1100) >> 2) + 1
    }

    /// Returns the size of the timestamp field in bytes.
    pub const fn size_timestamp(&self) -> u8 {
        ((self.0 & 0b0111_0000) >> 4) + 1
    }
}
impl From<u8> for RecordHeaderLengths {
    fn from(value: u8) -> Self {
        Self(value)
    }
}

fn parse_string(input: &[u8], len: usize) -> IResult<&[u8], &str, ParseError> {
    let (input, string_bytes) = bstreaming::take(len)(input)?;
    let string = std::str::from_utf8(string_bytes)
        .map_err(|_| nom::Err::Failure(ParseError::InvalidString))?;
    Ok((input, string))
}

#[derive(Debug, PartialEq, Eq)]
pub enum ParseError {
    InvalidFormat(nom::error::ErrorKind),
    InvalidVersion,
    InvalidString,
    InvalidIntegerSize,
    EOF,
}

impl std::fmt::Display for ParseError {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        match self {
            Self::InvalidFormat(kind) => write!(f, "Invalid format: {kind:?}"),
            Self::InvalidVersion => write!(f, "Invalid version"),
            Self::InvalidString => write!(f, "Invalid string"),
            Self::InvalidIntegerSize => write!(f, "Invalid integer size"),
            Self::EOF => write!(f, "EOF"),
        }
    }
}
impl std::error::Error for ParseError {}

impl<T> nom::error::ParseError<T> for ParseError {
    fn from_error_kind(_input: T, kind: nom::error::ErrorKind) -> Self {
        Self::InvalidFormat(kind)
    }

    fn append(_input: T, kind: nom::error::ErrorKind, _other: Self) -> Self {
        Self::InvalidFormat(kind)
    }
}

#[derive(Debug, Clone, PartialEq, Eq)]
pub enum Payload<'log> {
    // control records
    /// The Start control record provides information about the specified entry ID. It must appear prior to any records using that entry ID. The format of the Start control record’s payload data is as follows:
    Start {
        entry_id: u32,
        entry_name: &'log str,
        entry_type: &'log str,
        entry_metadata: &'log str,
    },
    /// The Finish control record indicates the entry ID is no longer valid. The format of the Finish control record’s payload data is as follows:
    Finish {
        entry_id: u32,
    },
    /// The Set metadata control record updates the metadata for an entry. The format of the record’s payload data is as follows:
    SetMetadata {
        entry_id: u32,
        entry_metadata: &'log str,
    },

    Raw {
        entry_id: u32,
        data: &'log [u8],
    },
}

#[derive(Debug, Clone)]
pub struct WpiRecord<'log> {
    pub timestamp: log::Timestamp,

    pub payload: Payload<'log>,
}

impl<'log> WpiRecord<'log> {
    const START_CONTROL_RECORD: u8 = 0x00;
    const FINISH_CONTROL_RECORD: u8 = 0x01;
    const SET_METADATA_CONTROL_RECORD: u8 = 0x02;

    fn parse_dyn_int(input: &[u8], size: u8) -> IResult<&[u8], u64, ParseError> {
        let (input, s) = bstreaming::take(size)(input)?;
        if s.len() > 8 {
            return Err(nom::Err::Failure(ParseError::InvalidIntegerSize));
        }

        let mut buf = [0; std::mem::size_of::<u64>()];
        buf[..s.len()].copy_from_slice(s);

        Ok((input, u64::from_le_bytes(buf)))
    }
    pub fn parse(input: &'log [u8]) -> IResult<&'log [u8], Self, ParseError> {
        let (input, lengths) = match nstreaming::u8(input) {
            Ok((input, lengths)) => (input, lengths),
            Err(nom::Err::Incomplete(_)) => {
                return Err(nom::Err::Error(ParseError::EOF));
            }
            e => e?,
        };
        let lengths = RecordHeaderLengths::from(lengths);

        let (input, entry_id) = Self::parse_dyn_int(input, lengths.size_entry_id())?;
        let (input, payload_len) = Self::parse_dyn_int(input, lengths.size_payload_len())?;
        let (input, timestamp) = Self::parse_dyn_int(input, lengths.size_timestamp())?;
        let timestamp = log::Timestamp(timestamp);

        let (leftover, input) = bstreaming::take(payload_len as usize)(input)?;

        if entry_id == 0 {
            let (input, control_record_type) = nstreaming::u8(input)?;
            let (input, entry_id) = nstreaming::le_u32(input)?;

            match control_record_type {
                Self::START_CONTROL_RECORD => {
                    let (input, entry_name_len) = nstreaming::le_u32(input)?;
                    let (input, entry_name) = parse_string(input, entry_name_len as usize)?;
                    let (input, entry_type_len) = nstreaming::le_u32(input)?;
                    let (input, entry_type) = parse_string(input, entry_type_len as usize)?;
                    let (input, entry_metadata_len) = nstreaming::le_u32(input)?;
                    let (input, entry_metadata) = parse_string(input, entry_metadata_len as usize)?;

                    require_consumed(input)?;

                    Ok((
                        leftover,
                        WpiRecord {
                            timestamp,
                            payload: Payload::Start {
                                entry_id,
                                entry_name,
                                entry_type,
                                entry_metadata,
                            },
                        },
                    ))
                }
                Self::FINISH_CONTROL_RECORD => {
                    require_consumed(input)?;

                    Ok((
                        leftover,
                        WpiRecord {
                            timestamp,
                            payload: Payload::Finish { entry_id },
                        },
                    ))
                }
                Self::SET_METADATA_CONTROL_RECORD => {
                    // the spec doesn't specify whether _this_ must be valid UTF-8 or not,
                    // but the previous one does, so let's assume it is
                    let (input, entry_metadata_len) = nstreaming::le_u32(input)?;
                    let (input, entry_metadata) = parse_string(input, entry_metadata_len as usize)?;

                    require_consumed(input)?;

                    Ok((
                        leftover,
                        WpiRecord {
                            timestamp,
                            payload: Payload::SetMetadata {
                                entry_id,
                                entry_metadata,
                            },
                        },
                    ))
                }
                _ => Err(nom::Err::Failure(ParseError::InvalidFormat(ErrorKind::Tag))),
            }
        } else {
            Ok((
                leftover,
                WpiRecord {
                    timestamp,
                    payload: Payload::Raw {
                        entry_id: entry_id as u32,
                        data: input,
                    },
                },
            ))
        }
    }
}

/// A simple binary logging format designed for high speed logging of timestamped data values (e.g. numeric sensor values).
#[derive(Debug, Clone, Default)]
pub struct WpiLogFile<'log> {
    /// version number.
    /// The most significant byte of the version
    /// indicates the major version and
    /// the least significant byte indicates the minor version.
    ///
    /// For this version of the data format, the value is thus 0x0100,
    /// indicating version 1.0.
    pub version: u16,
    /// The extra header string has arbitrary contents
    /// (e.g. the contents are set by the application that wrote the data log) but it must be UTF-8 encoded.
    pub extra_header: &'log str,

    /// There is no timestamp ordering requirement for records. This is true for control records as well—
    /// ​a Start control record with a later timestamp may be followed by data records for that entry with earlier timestamps.
    pub records: Vec<WpiRecord<'log>>,
}

impl<'log> WpiLogFile<'log> {
    #[must_use]
    pub fn is_wpilog(input: &[u8]) -> bool {
        input.starts_with(b"WPILOG")
    }

    fn parse_header(input: &'log [u8]) -> IResult<&'log [u8], (u16, &'log str), ParseError> {
        let (input, _) = nom::bytes::streaming::tag(&b"WPILOG"[..])(input)?;

        let (input, version) = nom::number::streaming::le_u16(input)?;
        if version != 0x0100 {
            return Err(nom::Err::Failure(ParseError::InvalidVersion));
        }

        let (input, header_len) = nom::number::streaming::le_u32(input)?;
        let (input, extra_header) = parse_string(input, header_len as usize)?;
        Ok((input, (version, extra_header)))
    }

    /// Parses every record of the log, handing each one to `record_cb` as it is read.
    ///
    /// Records are never retained, so importing a large log costs no more memory than the log
    /// itself. This is what the importer uses; [`WpiLogFile::parse`] is the variant that also
    /// keeps the record list.
    ///
    /// Damage is recovered from instead of propagated: a log pulled off the robot while it is
    /// still being written, or one with a scrambled stretch, imports up to the damage and then
    /// continues at the next intact record. Bytes that cannot be resynchronised are dropped with
    /// a warning, never turned into an error, because a truncated match log is still useful.
    pub fn parse_streaming(
        input: &'log [u8],
        mut record_cb: impl FnMut(WpiRecord<'log>),
    ) -> IResult<&'log [u8], (u16, &'log str), ParseError> {
        let (mut input, header) = match Self::parse_header(input) {
            Ok(parsed) => parsed,
            Err(_) => {
                // Records start at a fixed offset, so a damaged extra header only loses the
                // header string; the records after it may still be intact.
                re_log::warn!(
                    "the WPI log's header is damaged; looking for intact records after it"
                );
                let start = FIXED_HEADER_LEN.min(input.len());
                (&input[start..], (0x0100, ""))
            }
        };

        let mut last_timestamp = 0u64;
        let mut known_entries = HashSet::new();
        let mut skipped = 0usize;

        loop {
            if input.is_empty() {
                break;
            }
            match WpiRecord::parse(input) {
                Ok((remaining, record)) => {
                    match &record.payload {
                        Payload::Start { entry_id, .. } => {
                            known_entries.insert(*entry_id);
                        }
                        Payload::Finish { entry_id } => {
                            known_entries.remove(entry_id);
                        }
                        Payload::SetMetadata { .. } | Payload::Raw { .. } => {}
                    }
                    last_timestamp = last_timestamp.max(record.timestamp.0);
                    record_cb(record);
                    input = remaining;
                }
                Err(_) => match next_intact_record(input, last_timestamp, &known_entries) {
                    Some(offset) => {
                        re_log::warn!(
                            "skipping {offset} damaged bytes and resuming at the next intact \
                             record"
                        );
                        skipped += offset;
                        input = &input[offset..];
                    }
                    None => {
                        re_log::warn!(
                            "dropping the last {} bytes of the log: no intact record follows",
                            input.len()
                        );
                        skipped += input.len();
                        input = &input[input.len()..];
                        break;
                    }
                },
            }
        }

        if skipped > 0 {
            re_log::warn!("{skipped} bytes of the log could not be read");
        }

        Ok((input, header))
    }

    /// Parses the whole log, retaining every record in [`WpiLogFile::records`].
    pub fn parse(
        input: &'log [u8],
        mut record_cb: impl FnMut(WpiRecord<'log>),
    ) -> IResult<&'log [u8], Self, ParseError> {
        let mut records = Vec::new();
        let (input, (version, extra_header)) = Self::parse_streaming(input, |record| {
            records.push(record.clone());
            record_cb(record);
        })?;

        Ok((
            input,
            WpiLogFile {
                version,
                extra_header,
                records,
            },
        ))
    }
}

/// Rejects a control record whose payload has bytes left over.
///
/// The spec gives each control record an exact payload, so leftovers mean the bytes are not a
/// record at all. Checked in release builds too, because resynchronisation relies on a parse
/// failure meaning damage rather than a silently truncated control record.
fn require_consumed(input: &[u8]) -> Result<(), nom::Err<ParseError>> {
    if input.is_empty() {
        Ok(())
    } else {
        Err(nom::Err::Failure(ParseError::InvalidFormat(ErrorKind::Tag)))
    }
}

/// Looks for the next byte offset in a damaged region that starts intact records.
///
/// A WPI log is a flat record stream, so after a bad length or a scrambled payload the only way
/// back is to scan. One record is not evidence, since payload bytes can parse as records: a
/// candidate has to be followed by [`RESYNC_CHAIN`] records whose timestamps stay near the last
/// intact one and whose values belong to entries that have been started. Returns the offset of
/// the candidate, or `None` when none is found within [`RESYNC_MAX_SCAN_BYTES`].
fn next_intact_record(
    input: &[u8],
    last_timestamp: u64,
    known_entries: &HashSet<u32>,
) -> Option<usize> {
    let limit = if last_timestamp == 0 {
        u64::MAX
    } else {
        last_timestamp.saturating_add(RESYNC_MAX_GAP_US)
    };
    let earliest = last_timestamp.saturating_sub(RESYNC_MAX_BACKWARD_US);
    let scan = &input[..input.len().min(RESYNC_MAX_SCAN_BYTES)];

    (1..scan.len()).find(|offset| {
        let mut rest = &scan[*offset..];
        let mut started = [0u32; RESYNC_CHAIN];
        let mut started_len = 0;
        let mut previous = last_timestamp;
        for step in 0..RESYNC_CHAIN {
            if rest.is_empty() {
                // A chain that runs into the end of the file counts once it has a record behind
                // it; a truncated last record is not evidence of anything.
                return step > 0;
            }
            let Ok((remaining, record)) = WpiRecord::parse(rest) else {
                return false;
            };
            let timestamp = record.timestamp.0;
            if timestamp > limit || timestamp < earliest || timestamp < previous {
                return false;
            }
            previous = timestamp;
            match &record.payload {
                Payload::Start { entry_id, .. } => {
                    if started_len < RESYNC_CHAIN {
                        started[started_len] = *entry_id;
                        started_len += 1;
                    }
                }
                Payload::Raw { entry_id, .. }
                    if !known_entries.contains(entry_id)
                        && !started[..started_len].contains(entry_id) =>
                {
                    return false;
                }
                _ => {}
            }
            rest = remaining;
        }
        true
    })
}

#[cfg(test)]
mod tests {
    use std::num::NonZeroUsize;

    #[test]
    fn test_example_header() {
        // 57 50 49 4c 4f 47 00 01 00 00 00 00
        let example_header = [
            0x57, // W
            0x50, // P
            0x49, // I
            0x4c, // L
            0x4f, // O
            0x47, // G
            // 0x0100 - version
            0x00, // b0000_0000 - low byte of version
            0x01, // b0000_0001 - high byte of version
            0x00, 0x00, 0x00, 0x00, // b0000_0000 - length of extra header
        ];

        let (input, (version, extra_header)) =
            super::WpiLogFile::parse_header(&example_header).unwrap();

        assert_eq!(input.len(), 0);
        assert_eq!(extra_header, "");
        assert_eq!(version, 0x0100);
    }

    #[test]
    fn test_bad_magic() {
        let example_header = [
            0x00, // \0 - should be 'W'
            0x50, // P
            0x49, // I
            0x4c, // L
            0x4f, // O
            0x47, // G
            // 0x0100 - version
            0x00, // b0000_0000 - low byte of version
            0x01, // b0000_0001 - high byte of version
            0x00, 0x00, 0x00, 0x00, // b0000_0000 - length of extra header
        ];

        let err = super::WpiLogFile::parse_header(&example_header).unwrap_err();

        assert_eq!(
            err,
            nom::Err::Error(super::ParseError::InvalidFormat(nom::error::ErrorKind::Tag))
        );
    }

    #[test]
    fn test_bad_extra_header() {
        let example_header = [
            0x57, // W
            0x50, // P
            0x49, // I
            0x4c, // L
            0x4f, // O
            0x47, // G
            // 0x0100 - version
            0x00, // b0000_0000 - low byte of version
            0x01, // b0000_0001 - high byte of version
            0x01, 0x00, 0x00, 0x00, // b0000_0001 - length of extra header
        ];

        let err = super::WpiLogFile::parse_header(&example_header).unwrap_err();

        assert_eq!(
            err,
            nom::Err::Incomplete(nom::Needed::Size(NonZeroUsize::new(1).unwrap()))
        );
    }

    #[test]
    fn test_extra_header() {
        let example_header = [
            0x57, // W
            0x50, // P
            0x49, // I
            0x4c, // L
            0x4f, // O
            0x47, // G
            // 0x0100 - version
            0x00, // b0000_0000 - low byte of version
            0x01, // b0000_0001 - high byte of version
            0x01, 0x00, 0x00, 0x00, // b0000_0001 - length of extra header
            b'a', // extra header
        ];

        let (input, (_version, extra_header)) =
            super::WpiLogFile::parse_header(&example_header).unwrap();

        assert_eq!(input.len(), 0);

        assert_eq!(extra_header, "a");
    }

    #[test]
    fn test_example_record() {
        // 20 (ID length = 1 byte, payload size length = 1 byte, timestamp length = 3 bytes)
        //
        // 01 (entry ID = 1)
        //
        // 08 (payload size = 8 bytes)
        //
        // 40 42 0f (timestamp = 1,000,000 us)
        //
        // 03 00 00 00 00 00 00 00 (value = 3)
        //

        let example_record = [
            0x20, // b0010_0000 - ID length = 1 byte, payload size length = 1 byte, timestamp length = 3 bytes
            0x01, // b0000_0001 - entry ID = 1
            0x08, // b0000_1000 - payload size = 8 bytes
            0x40, 0x42, 0x0f, // timestamp = 1,000,000 us
            //
            0x03, 0x00, 0x00, 0x00, //
            0x00, 0x00, 0x00, 0x00,
        ];

        let (input, record) = super::WpiRecord::parse(&example_record).unwrap();

        assert_eq!(input.len(), 0);

        assert_eq!(record.timestamp.0, 1_000_000);
        assert_eq!(
            record.payload,
            super::Payload::Raw {
                entry_id: 1,
                data: &[
                    0x03, 0x00, 0x00, 0x00, //
                    0x00, 0x00, 0x00, 0x00
                ],
            }
        );
    }

    #[test]
    fn test_start_cr() {
        // 20 (ID length = 1 byte, payload size length = 1 byte, timestamp length = 3 bytes)
        //
        // 00 (entry ID = 0)
        //
        // 1a (payload size = 26 bytes)
        //
        // 40 42 0f (timestamp = 1,000,000 us)
        //
        // 00 (control record type = Start (0))
        //
        // 01 00 00 00 (entry ID 1 being started)
        //
        // 04 00 00 00 (length of name string = 4)
        //
        // 74 65 73 74 (entry name = test)
        //
        // 05 00 00 00 (length of type string = 5)
        //
        // 69 6e 74 66 64 (type string = int64)
        //
        // 00 00 00 00 (length of metadata string = 0)

        let example_record = [
            0x20, // b0010_0000 - ID length = 1 byte, payload size length = 1 byte, timestamp length = 3 bytes
            0x00, // b0000_0000 - entry ID = 0
            0x1a, // b0001_1010 - payload size = 26 bytes
            0x40, 0x42, 0x0f, // timestamp = 1,000,000 us
            //
            0x00, // b0000_0000 - control record type = Start (0)
            //
            0x01, 0x00, 0x00, 0x00, // entry ID 1 being started
            //
            0x04, 0x00, 0x00, 0x00, // length of name string = 4
            //
            b't', b'e', b's', b't', // entry name = test
            //
            0x05, 0x00, 0x00, 0x00, // length of type string = 5
            //
            b'i', b'n', b't', b'6', b'4', // type string = int64
            //
            0x00, 0x00, 0x00, 0x00, // metadata string length = 0
        ];

        let (input, record) = super::WpiRecord::parse(&example_record).unwrap();

        assert_eq!(input.len(), 0);

        assert_eq!(record.timestamp.0, 1_000_000);
        assert_eq!(
            record.payload,
            super::Payload::Start {
                entry_id: 1,
                entry_name: "test",
                entry_type: "int64",
                entry_metadata: "",
            }
        );
    }

    #[test]
    fn test_finish_cr() {
        // 20 (ID length = 1 byte, payload size length = 1 byte, timestamp length = 3 bytes)
        //
        // 00 (entry ID = 0)
        //
        // 05 (payload size = 5 bytes)
        //
        // 40 42 0f (timestamp = 1,000,000 us)
        //
        // 01 (control record type = Finish (1))
        //
        // 01 00 00 00 (entry ID being finished)

        let example_record = [
            0x20, // b0010_0000 - ID length = 1 byte, payload size length = 1 byte, timestamp length = 3 bytes
            0x00, // b0000_0000 - entry ID = 0
            0x05, // b0000_1000 - payload size = 5 bytes
            0x40, 0x42, 0x0f, // timestamp = 1,000,000 us
            //
            0x01, // b0000_0001 - control record type = Finish (1)
            //
            0x01, 0x00, 0x00, 0x00, // entry ID being finished
        ];

        let (input, record) = super::WpiRecord::parse(&example_record).unwrap();

        assert_eq!(input.len(), 0);

        assert_eq!(record.timestamp.0, 1_000_000);
        assert_eq!(record.payload, super::Payload::Finish { entry_id: 1 });
    }

    #[test]
    fn test_set_metadata_cr() {
        // 20 (ID length = 1 byte, payload size length = 1 byte, timestamp length = 3 bytes)
        //
        // 00 (entry ID = 0)
        //
        // 18 (payload size = 24 bytes)
        //
        // 40 42 0f (timestamp = 1,000,000 us)
        //
        // 02 (control record type = Set Metadata (2))
        //
        // 01 00 00 00 (setting metadata for entry ID 1)
        //
        // 0f 00 00 00 (length of metadata string = 15)
        //
        // 7b 22 73 6f 75 72 63 65 22 3a 22 4e 54 22 7d (metadata string = {"source":"NT"})

        let example_record = [
            0x20, // b0010_0000 - ID length = 1 byte, payload size length = 1 byte, timestamp length = 3 bytes
            0x00, // b0000_0000 - entry ID = 0
            0x18, // b0001_1000 - payload size = 24 bytes
            0x40, 0x42, 0x0f, // timestamp = 1,000,000 us
            //
            0x02, // b0000_0010 - control record type = Set Metadata (2)
            //
            0x01, 0x00, 0x00, 0x00, // setting metadata for entry ID 1
            //
            0x0f, 0x00, 0x00, 0x00, // length of metadata string = 15
            //
            b'{', b'"', b's', b'o', b'u', b'r', b'c', b'e', b'"', b':', b'"', b'N', b'T', b'"',
            b'}',
        ];

        let (input, record) = super::WpiRecord::parse(&example_record).unwrap();

        assert_eq!(input.len(), 0);

        assert_eq!(record.timestamp.0, 1_000_000);
        assert_eq!(
            record.payload,
            super::Payload::SetMetadata {
                entry_id: 1,
                entry_metadata: r#"{"source":"NT"}"#,
            }
        );
    }

    #[test]
    fn test_multi_record() {
        let mut file = Vec::new();

        let file_header = [
            0x57, // W
            0x50, // P
            0x49, // I
            0x4c, // L
            0x4f, // O
            0x47, // G
            // 0x0100 - version
            0x00, // b0000_0000 - low byte of version
            0x01, // b0000_0001 - high byte of version
            0x00, 0x00, 0x00, 0x00, // b0000_0000 - length of extra header
        ];

        file.extend_from_slice(&file_header);

        let start_record = [
            0x20, // b0010_0000 - ID length = 1 byte, payload size length = 1 byte, timestamp length = 3 bytes
            0x00, // b0000_0000 - entry ID = 0
            0x2b, // b101011 - payload size = 43 bytes
            0x40, 0x42, 0x0f, // timestamp = 1,000,000 us
            //
            0x00, // b0000_0000 - control record type = Start (0)
            //
            0x01, 0x00, 0x00, 0x00, // entry ID 1 being started
            //
            0x05, 0x00, 0x00, 0x00, // length of name string = 5
            //
            b'r', b'e', b'r', b'u', b'n', // entry name = rerun
            //
            0x05, 0x00, 0x00, 0x00, // length of type string = 5
            //
            b'i', b'n', b't', b'6', b'4', // type string = int64
            //
            0x10, 0x00, 0x00, 0x00, // length of metadata string = 16
            //
            b'{', b'"', b's', b'o', b'u', b'r', b'c', b'e', b'"', b':', b'"', b'l', b'o', b'g',
            b'"', b'}', // metadata string = {"source":"log"}
        ];

        file.extend_from_slice(&start_record);

        let raw_record = [
            0x20, // b0010_0000 - ID length = 1 byte, payload size length = 1 byte, timestamp length = 3 bytes
            0x01, // b0000_0001 - entry ID = 1
            0x04, // b0000_0100 - payload size = 4 bytes
            0x72, 0x42, 0x0f, // timestamp = 1,000,050 us
            //
            b'c', b'o', b'o', b'l', //
        ];

        file.extend_from_slice(&raw_record);

        let finish_record = [
            0x20, // b0010_0000 - ID length = 1 byte, payload size length = 1 byte, timestamp length = 3 bytes
            0x00, // b0000_0000 - entry ID = 0
            0x05, // b0000_0101 - payload size = 5 bytes
            0xA4, 0x42, 0x0f, // timestamp = 1,000,100 us
            //
            0x01, // b0000_0001 - control record type = Finish (1)
            //
            0x01, 0x00, 0x00, 0x00, // entry ID being finished
        ];

        file.extend_from_slice(&finish_record);

        let (input, wpi_log) = super::WpiLogFile::parse(&file, |_| {}).unwrap();

        assert_eq!(input.len(), 0);

        assert_eq!(wpi_log.version, 0x0100);
        assert_eq!(wpi_log.extra_header, "");
        assert_eq!(wpi_log.records.len(), 3);
        assert_eq!(wpi_log.records[0].timestamp.0, 1_000_000);
        assert_eq!(
            wpi_log.records[0].payload,
            super::Payload::Start {
                entry_id: 1,
                entry_name: "rerun",
                entry_type: "int64",
                entry_metadata: r#"{"source":"log"}"#,
            }
        );
        assert_eq!(wpi_log.records[1].timestamp.0, 1_000_050);
        assert_eq!(
            wpi_log.records[1].payload,
            super::Payload::Raw {
                entry_id: 1,
                data: b"cool",
            }
        );
        assert_eq!(wpi_log.records[2].timestamp.0, 1_000_100);
        assert_eq!(
            wpi_log.records[2].payload,
            super::Payload::Finish { entry_id: 1 }
        );
    }

    #[test]
    fn test_real_world() {
        let example = include_bytes!("../../test_data/FRC_TBD_d225b5377c70a88d.wpilog");

        let (input, _wpi_log) = super::WpiLogFile::parse(example, |_| {}).unwrap();

        assert_eq!(input.len(), 0);
    }

    /// The fixed header of a WPI log, with no extra header string.
    fn header() -> Vec<u8> {
        let mut file = b"WPILOG".to_vec();
        file.extend_from_slice(&0x0100u16.to_le_bytes());
        file.extend_from_slice(&0u32.to_le_bytes());
        file
    }

    /// One record with 1-byte entry ids, 1-byte payload lengths and 3-byte timestamps.
    fn push_record(file: &mut Vec<u8>, entry_id: u8, timestamp: u32, payload: &[u8]) {
        assert!(payload.len() < 128 && timestamp < 1 << 24);
        file.push(0x20);
        file.push(entry_id);
        file.push(payload.len() as u8);
        file.extend_from_slice(&timestamp.to_le_bytes()[..3]);
        file.extend_from_slice(payload);
    }

    /// A Start control record for an `int64` entry.
    fn push_start(file: &mut Vec<u8>, entry_id: u32, timestamp: u32) {
        let mut payload = vec![0];
        payload.extend_from_slice(&entry_id.to_le_bytes());
        for string in ["test", "int64", ""] {
            payload.extend_from_slice(&(string.len() as u32).to_le_bytes());
            payload.extend_from_slice(string.as_bytes());
        }
        push_record(file, 0, timestamp, &payload);
    }

    #[test]
    fn resumes_after_a_damaged_record() {
        let mut file = header();
        push_start(&mut file, 1, 1_000_000);
        push_record(&mut file, 1, 1_000_001, b"ab");
        push_record(&mut file, 1, 1_000_002, b"cd");
        // A scrambled stretch, sized so nothing in it suggests a record chain.
        file.extend_from_slice(&[0xde, 0xad, 0xbe, 0xef, 0x00, 0x00, 0x00]);
        for step in 0..6 {
            push_record(&mut file, 1, 1_000_100 + step, b"ef");
        }

        let mut timestamps = Vec::new();
        super::WpiLogFile::parse_streaming(&file, |record| {
            if matches!(record.payload, super::Payload::Raw { .. }) {
                timestamps.push(record.timestamp.0);
            }
        })
        .unwrap();

        assert_eq!(timestamps[..2], [1_000_001, 1_000_002]);
        assert_eq!(timestamps.len(), 8, "the records after the damage are read");
        assert_eq!(timestamps[2], 1_000_100);
    }

    #[test]
    fn stops_cleanly_at_a_truncated_last_record() {
        let mut file = header();
        push_start(&mut file, 1, 1_000_000);
        push_record(&mut file, 1, 1_000_001, b"ab");
        // A record header whose 64-byte payload never arrives.
        file.extend_from_slice(&[0x20, 0x01, 0x40, 0x42]);

        let mut count = 0;
        super::WpiLogFile::parse_streaming(&file, |_| count += 1).unwrap();

        assert_eq!(count, 2, "everything before the cut is imported");
    }
}
