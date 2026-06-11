# Project: DueProcess Modernization

## Architecture
- Android app, Gradle build system.
- Main components: AppIntro slides, Setup provisioning, UI management, test suites.

## Milestones
| # | Name | Scope | Dependencies | Status |
|---|------|-------|-------------|--------|
| 1 | AppIntro & Atomic Commits | R1 & R2: Resolve AppIntro version to 6.3.1, apply code changes, and commit the existing working tree as atomic commits per HANDOFF.md. | none | PLANNED |
| 2 | Test Suites Port | R3: Port unit and UI tests from `main` branch (commits a8f0635 and bfae726) to work on `modernize` branch. Update tests for modernized APIs. | M1 | PLANNED |
| 3 | Verification | R4: Verify `assembleDebug`, `assembleRelease`, `lintDebug`, and `./gradlew test` pass successfully. Update code if needed. | M2 | PLANNED |
