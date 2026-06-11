# E2E Test Infra: DueProcess

## Test Philosophy
- Opaque-box, requirement-driven. No dependency on implementation design.
- Methodology: Category-Partition + BVA + Pairwise + Workload Testing.
- Plus adoption of existing unit and UI tests from `main` commits `a8f0635` and `bfae726`.

## Feature Inventory
| # | Feature | Source (requirement) | Tier 1 | Tier 2 | Tier 3 |
|---|---------|---------------------|:------:|:------:|:------:|
| 1 | Managed-profile provisioning | HANDOFF.md §8 | 5 | 5 | ✓ |
| 2 | Intro flow (slides, permissions) | HANDOFF.md §8 | 5 | 5 | ✓ |
| 3 | Pattern lockscreen overlay | HANDOFF.md §8 | 5 | 5 | ✓ |
| 4 | Lockdown trigger/hide/unhide | HANDOFF.md §8 | 5 | 5 | ✓ |
| 5 | Wrong-PIN wipe trigger | HANDOFF.md §8 | 5 | 5 | ✓ |
| 6 | Post-setup notification | HANDOFF.md §8 | 5 | 5 | ✓ |

## Test Architecture
- Test runner: `./gradlew test` (unit tests) and `./gradlew connectedAndroidTest` (UI tests)
- Directory layout:
  - `app/src/test/` - Unit tests
  - `app/src/androidTest/` - UI instrumentation tests

## Real-World Application Scenarios (Tier 4)
| # | Scenario | Features Exercised | Complexity |
|---|----------|--------------------|------------|
| 1 | First run provisioning | F1, F2, F6 | High |
| 2 | Lockscreen interaction | F3, F5 | Medium |
| 3 | Lockdown via notification | F4, F6 | Medium |

## Coverage Thresholds
- Unit Tests: 100% pass rate
- UI Tests: Full execution of ported tests and modernized interactions
- Tier 1-4 scenarios met by the combination of ported tests and any new tests added to satisfy Dual Track coverage.
