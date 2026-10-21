package org.teamvan.moneyshift;

/** Versions and identifiers of the Rerun protocol this SDK speaks. */
public final class RerunVersion {
    /** The Rerun version whose type definitions and wire format this SDK implements. */
    public static final int MAJOR = 0;
    public static final int MINOR = 38;
    public static final int PATCH = 1;

    public static final String VERSION = MAJOR + "." + MINOR + "." + PATCH;

    /** {@code CrateVersion::to_bytes} packed little-endian into an i32. */
    public static final int STORE_VERSION_BITS = MAJOR | (MINOR << 8) | (PATCH << 16);

    /** Value sent in the {@code x-rerun-client-version} gRPC header. */
    public static final String CLIENT_IDENTIFIER = "moneyshift-java/0.1.0";

    public static final String SDK_NAME = "moneyshift";

    private RerunVersion() {}
}
