// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Whether a {@link org.teamvan.moneyshift.components.VideoSample rerun::components::VideoSample} contains a keyframe (also known as a sync sample or IDR).
 * 
 * A keyframe in this sense must be _decoder re-entrant_: a decoder must be able to start
 * decoding the stream from this sample alone, with no prior decoder state.
 * Not every intra-coded frame qualifies. Some codecs have intra-only frames that may
 * still reference existing decoder state and are therefore not valid sync points.
 * See {@link org.teamvan.moneyshift.components.VideoCodec rerun::components::VideoCodec} for the codec-specific definition of a keyframe.
 */
public record IsKeyframe(
    org.teamvan.moneyshift.encodings.Bool isKeyframe
) implements org.teamvan.moneyshift.NtComponent<IsKeyframe> {
    public IsKeyframe {
        java.util.Objects.requireNonNull(isKeyframe, "isKeyframe");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public IsKeyframe(boolean value) {
        this(new org.teamvan.moneyshift.encodings.Bool(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.IsKeyframe";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<IsKeyframe> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).isKeyframe();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        boolean[] column1 = new boolean[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).isKeyframe();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? false : value1;
        }
        w.booleans(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<IsKeyframe> values) {
        writeColumnsStatic(w, path, values);
    }
}
