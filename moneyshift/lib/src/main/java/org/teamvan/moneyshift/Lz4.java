package org.teamvan.moneyshift;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4FastDecompressor;

/** Rerun uses raw LZ4 block compression, which is what lz4-java's compressors produce. */
public final class Lz4 {
    private static final LZ4Compressor COMPRESSOR = net.jpountz.lz4.LZ4Factory.fastestInstance()
            .fastCompressor();
    private static final LZ4FastDecompressor DECOMPRESSOR = net.jpountz.lz4.LZ4Factory.fastestInstance()
            .fastDecompressor();

    private Lz4() {}

    public static byte[] compress(byte[] data) {
        return COMPRESSOR.compress(data);
    }

    public static byte[] decompress(byte[] data, int uncompressedSize) {
        return DECOMPRESSOR.decompress(data, uncompressedSize);
    }
}
