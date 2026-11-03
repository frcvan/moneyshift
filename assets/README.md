# Bundled models

## `kitbot.glb`

The drivebase-only KitBot model AdvantageScope ships for the 3D field, and the default robot model
the bridge draws when `moneyshift.toml`/`--robot-model` names none.

- **Source:** `bundledAssets/Robot_FRCKitBot/model.glb` in
  [Mechanical-Advantage/AdvantageScope](https://github.com/Mechanical-Advantage/AdvantageScope),
  copyright Littleton Robotics; BSD-2-Clause, see `AdvantageScope-LICENSE`.
- **Baked transform:** AdvantageScope's `bundledAssets/Robot_FRCKitBot/config.json` places the CAD
  model with a +90° rotation about `z` followed by the position `[0.12, 3.17, 0]`. Those are
  applied to the vertices and normals here, so the model is already in the robot frame: `+x`
  forward, `+y` left, `+z` up, footprint center at the origin, base on `z = 0`. The geometry is
  otherwise unchanged.

`src/first.rs` embeds this file with `include_bytes!`.
