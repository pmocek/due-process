# Original User Request

## Initial Request — 2026-06-11T15:14:07Z

# Teamwork Project Prompt — Draft

Modernize the DueProcess codebase to build and run on current Android/Gradle releases (AGP 8.13.2, Gradle 8.14.3, SDK 36), resolve AppIntro API version mismatch, commit remaining changes, and adopt/verify tests from the `main` branch.

Working directory: /home/pmocek/sandbox/pmocek/dueprocess
Integrity mode: development

## Requirements

### R1. Resolve AppIntro Dependency and Code Discrepancy
- Update `app/build.gradle` to declare `com.github.AppIntro:AppIntro:6.3.1` (Option A from HANDOFF.md).
- Ensure `IntroActivity.java` and `SampleSlideFragment.java` are fully ported and compile against the AppIntro v6 API.

### R2. Commit Unstaged Changes Atomically
- Stage and commit the remaining unstaged changes (AppIntro migration and permissions self-grant in `PostProvisioningTask.java`) according to the atomic commit plan in HANDOFF.md.

### R3. Adopt and Modernize Existing Test Suites
- Port and adapt the unit tests from `main` commit `a8f0635` (e.g., `AppSettingsTest.java`, `HidingUtilTest.java`) to work on the `modernize` branch.
- Port and adapt the UI instrumentation tests from `main` commit `bfae726` (e.g., `MainActivityTest.java`, `PatternTouchAction.java`) to work on the modernized codebase.
- Review and update these tests to cover modernized APIs (such as the AppIntro v6 flow and notification PendingIntent immutability).

### R4. Build and Test Verification
- Verify that `assembleDebug`, `assembleRelease`, `lintDebug`, and `./gradlew test` (unit tests) run and pass successfully.

## Acceptance Criteria

### Build & Test Success
- [ ] `./gradlew assembleDebug` compiles successfully without errors.
- [ ] `./gradlew assembleRelease` compiles successfully without errors.
- [ ] `./gradlew test` executes all unit tests successfully with 100% pass rate.
- [ ] `./gradlew lintDebug` runs and reports 0 errors.

### Commits
- [ ] Git working tree is clean.
- [ ] Commits follow the structure outlined in the handoff document, plus the test porting commits.
