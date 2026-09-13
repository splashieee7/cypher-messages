# Cypher Messages

A dark, emerald-accented SMS/MMS app for Android — built for GrapheneOS Pixels as a drop-in replacement for the stock Messaging app.

Cypher Messages is a themed fork of [Fossify Messages](https://github.com/FossifyOrg/Messages) (GPL-3.0). All messaging functionality is upstream's; this fork changes the identity and the look:

- Package id `one.cypherph.messages` — installs alongside, and replaces, the stock app without conflicts
- Near-black base (`#070A08`) with emerald (`#10B981`) accent as the out-of-the-box palette
- Material You (dynamic colour) off by default so the palette shows on Android 12+; the user can still switch it on in Settings → Customize colours
- Sent bubbles in emerald with black text; received bubbles in a faint emerald wash
- Space Grotesk as the app typeface (SIL OFL 1.1, bundled)
- Launcher icon: emerald glyph on near-black, with a monochrome variant for themed icons
- No internet permission, no analytics, no Google dependencies (the `foss` flavour)

Everything else — scheduled messages, backup/export, blocked numbers and keywords, app lock, lock-screen privacy, group MMS — is straight from upstream.

## Building

The simplest path is GitHub Actions: push this repo (a private repo is fine) and the workflow in `.github/workflows/build.yml` builds `assembleFossRelease` on every push to `main` and attaches the APK as a build artifact. Tag a commit `v1.0.0` and it also publishes a GitHub Release with the APK, which Obtainium can track for updates.

To get a *signed* APK from CI, add four repository secrets (Settings → Secrets and variables → Actions):

| Secret | Value |
| --- | --- |
| `SIGNING_STORE_BASE64` | `base64 -w0 keystore.jks` |
| `SIGNING_STORE_PASSWORD` | keystore password |
| `SIGNING_KEY_ALIAS` | `cypher` |
| `SIGNING_KEY_PASSWORD` | key password |

Locally: put `keystore.jks` in the repo root and copy `keystore.properties_sample` to `keystore.properties` with the real values, then

```
./gradlew assembleFossRelease
```

Requires JDK 17+ and the Android SDK (platform 36). The APK lands in `app/build/outputs/apk/foss/release/`.

**Keep the keystore safe.** Android only allows updates signed with the same key; lose it and every phone needs a reinstall.

## Installing on GrapheneOS

Sideload the APK (adb, or drop it into your flashing pipeline), open it once, and accept the prompt to make it the default SMS app. You can also set it under Settings → Apps → Default apps → SMS app. The stock Messaging app can then be disabled.

## Keeping up with upstream

This fork stays close to upstream so rebases are cheap. The changes live in:

- `gradle.properties` — `APP_ID`, version
- `app/src/main/res/values/colors.xml` — the palette (shadows Commons defaults)
- `app/src/main/res/values/styles.xml` + `app/src/main/res/font/` — typeface
- `app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml`, `drawable/ic_launcher_foreground*.xml` — icon
- `helpers/Config.kt` (`applyCypherThemeDefaults`) and `App.kt` — Material You default
- Kotlin package moved from `org.fossify.messages` to `one.cypherph.messages`

To pull upstream changes: `git remote add upstream https://github.com/FossifyOrg/Messages.git && git fetch upstream && git merge upstream/main`, then re-apply the package rename if new files were added under `org.fossify.messages`.

## License

GPL-3.0, same as upstream. See `LICENSE`. Space Grotesk is licensed under the SIL Open Font License 1.1 (`graphics/OFL_SpaceGrotesk.txt`).
