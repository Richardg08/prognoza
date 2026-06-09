# Test Documentation

## 1. Introduction

Prognoza is an open-source Android weather application. The app retrieves weather forecast data from freely available REST-based weather providers, stores relevant data locally, and visualizes current and upcoming weather information for user-selected places.

The project uses Kotlin and modern Android technologies. The Android app itself is located in `androidApp`, while the shared weather logic is located in `shared`. This separation is important for testing: many business rules can be tested without starting an Android emulator, similar to testing normal Python modules with `pytest`.

The goal of this test documentation is to describe how the application should be tested, which areas are in scope, and which quality criteria must be fulfilled before the project is accepted.

## 2. Project Under Test

The main application features are:

- Search for places.
- Select and save a place.
- Retrieve weather forecast data from open weather APIs.
- Display current weather and upcoming forecast data.
- Change measurement units and forecast provider settings.
- Cache weather data for offline or reduced-network usage.
- Display weather information in an Android widget.

The app depends on several open-source components and services, including Open-Meteo, MET Norway, OpenStreetMap Nominatim, Ktor, SQLDelight, Jetpack Compose, Hilt, and WorkManager.

## 3. Test Objectives

The testing process should verify that:

- The app fulfills the functional requirements of a weather forecast application.
- The app handles common user workflows correctly.
- Weather data is fetched, transformed, cached, and displayed correctly.
- Settings such as units and provider selection affect the app as expected.
- The app behaves acceptably when network requests fail or no place is selected.
- Existing business logic is protected by automated tests.
- The application is stable enough to run on an Android emulator for the final demonstration.

## 4. Test Scope

In scope:

- Unit tests for shared business logic, such as unit conversion and forecast grouping.
- Functional tests for user workflows, such as selecting a place and viewing a forecast.
- UI tests for important Compose components where automation is practical.
- Manual exploratory tests on an emulator.
- Basic non-functional tests for usability, reliability, offline behavior, and performance perception.

Out of scope for this project phase:

- Full production-grade security testing.
- Large-scale load testing of external weather APIs.
- Testing all Android device models and OS versions.
- Verification of the scientific correctness of third-party weather predictions.

## 5. Testing Approach

The project should be tested using a combination of automated and manual tests.

Automated tests should focus on stable logic that can be checked repeatedly. In Python terms, this is similar to testing pure functions and service classes before testing the full application. In this project, the best candidates are the files in the `shared` module, because they contain forecast models, unit conversion, settings logic, and data transformation.

Manual tests should focus on complete user journeys. These are easier to validate on an emulator because they include Android-specific behavior such as navigation, Compose UI rendering, local storage, and widget behavior.

The recommended test levels are:

- Unit testing: verify isolated logic in the `shared` module.
- Integration testing: verify interaction between use cases, repositories, API services, and database cache.
- UI testing: verify selected Compose components and visible user states.
- Manual system testing: verify complete workflows on an Android emulator.

## 6. Requirements and Test Basis

This section defines the requirements that are used as the basis for testing. They are derived from the project assignment and from the existing feature set of the application. In Python project terms, this is similar to writing down what behavior a `pytest` suite should prove before deciding which test files and test functions are needed.

| Requirement ID | Requirement | Acceptance criteria | Test coverage |
| --- | --- | --- | --- |
| REQ-001 | The app shall use freely available weather data from REST-based APIs. | Weather data can be requested from at least one supported open weather provider. The provider usage is documented and visible in the codebase. | MT-001, MT-006 |
| REQ-002 | The app shall display current weather and forecast information for a selected place. | After a place is selected and data is available, the app displays current weather and forecast sections without crashing. | UT-025, UT-026, UT-027, UT-028, MT-002, MT-003 |
| REQ-003 | The app shall allow users to search for and select places. | A user can search for a place, select it, and use it as the active forecast location. Invalid coordinates are rejected by the domain model. | UT-004, UT-005, UT-006, MT-002 |
| REQ-004 | The app shall support different measurement units. | Temperature, speed, precipitation/length, pressure, percentage, and angle values are converted correctly where applicable. | UT-001, UT-002, UT-003, UT-022, UT-023, UT-024, UT-029, UT-030, UT-031, UT-032, UT-037, UT-038, UT-039, UT-040, UT-041, UT-042, UT-043, UT-044, UT-045, UT-046, UT-047, UT-048, UT-049, UT-050, UT-051, MT-004 |
| REQ-005 | The app shall correctly process weather-specific domain logic. | Forecast data is grouped into current, today, and coming-day sections. Wind chill and day/night calculation behave according to known expected values. | UT-007, UT-008, UT-009, UT-010, UT-011, UT-012, UT-013, UT-014, UT-015, UT-016, UT-017, UT-018, UT-019, UT-020, UT-021, UT-025, UT-026, UT-027, UT-028, UT-033, UT-034, UT-035, UT-036 |
| REQ-006 | The app shall handle loading states in the UI in a controlled way. | The loading indicator is not shown too early, remains visible long enough to avoid flicker, and follows the latest loading state. | UI-001, UI-002, UI-003, UI-004, UI-005, UI-006, UI-007, MT-003 |
| REQ-007 | The app shall run on an Android emulator for demonstration. | The app builds, installs, starts, and supports the main weather workflow on an emulator. | MT-001, MT-002, MT-003 |
| REQ-008 | The app shall tolerate missing data, failed requests, or unavailable network conditions without critical crashes. | The app shows an error or empty state instead of crashing when required data is unavailable. | UT-025, MT-005 |
| REQ-009 | The app shall use and document open-source components. | Important FLOSS components and external data providers are listed in the project documentation. | Documentation review |

The `UT-*` and `UI-*` identifiers refer to existing automated tests. The `MT-*` identifiers refer to planned manual/system test cases that should be documented separately.

### 6.1 Requirement Traceability Matrix

| Requirement ID | Existing automated tests | Planned manual/system tests | Coverage status |
| --- | --- | --- | --- |
| REQ-001 | None currently. API provider code exists, but no automated API integration test is documented. | MT-001, MT-006 | Partially covered |
| REQ-002 | UT-025, UT-026, UT-027, UT-028 | MT-002, MT-003 | Covered by unit and manual tests |
| REQ-003 | UT-004, UT-005, UT-006 | MT-002 | Partially covered |
| REQ-004 | UT-001, UT-002, UT-003, UT-022, UT-023, UT-024, UT-029, UT-030, UT-031, UT-032, UT-037, UT-038, UT-039, UT-040, UT-041, UT-042, UT-043, UT-044, UT-045, UT-046, UT-047, UT-048, UT-049, UT-050, UT-051 | MT-004 | Covered by unit and manual tests |
| REQ-005 | UT-007, UT-008, UT-009, UT-010, UT-011, UT-012, UT-013, UT-014, UT-015, UT-016, UT-017, UT-018, UT-019, UT-020, UT-021, UT-025, UT-026, UT-027, UT-028, UT-033, UT-034, UT-035, UT-036 | MT-003 | Covered by unit and manual tests |
| REQ-006 | UI-001, UI-002, UI-003, UI-004, UI-005, UI-006, UI-007 | MT-003 | Covered by UI and manual tests |
| REQ-007 | None currently. Emulator execution is verified manually. | MT-001, MT-002, MT-003 | Manual coverage required |
| REQ-008 | UT-025 | MT-005 | Partially covered |
| REQ-009 | None required as runtime test. | Documentation review | Documentation coverage |

### 6.2 Planned Manual Test IDs

The manual test cases are intentionally listed here before their detailed steps are written. This allows the traceability matrix to show planned coverage early.

| Test ID | Planned test area | Purpose |
| --- | --- | --- |
| MT-001 | App installation and startup on emulator | Verify that the app builds, installs, and launches on an Android emulator. |
| MT-002 | Place search and selection | Verify that a user can search for a place and select it as active forecast location. |
| MT-003 | Forecast display workflow | Verify that current weather and forecast sections are displayed for the selected place. |
| MT-004 | Settings and unit changes | Verify that changing units affects displayed weather values. |
| MT-005 | Error, empty, and offline behavior | Verify that unavailable network or missing data does not cause a critical crash. |
| MT-006 | Forecast provider selection | Verify that supported forecast providers can be selected and used. |

## 7. Manual Functional And System Tests

The manual tests are executed as a hybrid system test approach: the app is run on an Android emulator, core workflows are executed manually, and Logcat is observed for crashes or unexpected errors. This gives practical end-to-end coverage without introducing new automated UI tests shortly before submission.

Manual test data is stored separately in `docs/manual-test-data.md`. This keeps the test procedure reusable and avoids hardcoding all test data directly into the test steps.

### 7.1 Manual Test Tools

| Tool | Purpose |
| --- | --- |
| Android Studio | Build, install, and run the app on an emulator. |
| Android Emulator | Execute the application in an environment accepted by the assignment. |
| Logcat | Observe crashes, stack traces, permission errors, and network-related errors during manual execution. |
| Optional `adb` | Reset app data or check connected devices from the command line. |

Recommended optional `adb` commands:

```bash
adb devices
adb shell pm clear hr.dtakac.prognoza.debug
```

### 7.2 Evidence Convention

Manual test results should be documented directly in the test table. If additional evidence is needed, use predictable names for logs or notes:

| Evidence type | Suggested path |
| --- | --- |
| MT-001 notes | `docs/test-evidence/MT-001-app-start.md` |
| MT-002 notes | `docs/test-evidence/MT-002-place-search.md` |
| MT-003 notes | `docs/test-evidence/MT-003-forecast-display.md` |
| MT-004 notes | `docs/test-evidence/MT-004-unit-settings.md` |
| MT-005 log excerpt | `docs/test-evidence/MT-005-error-offline.md` |
| MT-006 notes | `docs/test-evidence/MT-006-provider-selection.md` |

### 7.3 Manual Functional Test Cases

| Field | Value |
| --- | --- |
| Test ID | MT-001 |
| Test name | App installation and startup on emulator |
| Test level | Manual system test |
| Requirement reference | REQ-001, REQ-007 |
| Preconditions | Android Studio is installed. An Android emulator is available. The project has been opened and Gradle sync has completed. |
| Test data | TD-ENV-001 |
| Test steps | 1. Start the Android emulator. 2. Build and run the app from Android Studio. 3. Wait until the launch screen finishes. 4. Observe whether the main UI is displayed. 5. Check Logcat for critical crash output. |
| Expected result | The app installs, launches, and displays its main UI without a critical crash. |
| Actual result | App installed and launched successfully on the Android emulator. The main UI was displayed after startup. |
| Status | Passed. |
| Notes / evidence | No critical crash was observed in Logcat during startup. |

| Field | Value |
| --- | --- |
| Test ID | MT-002 |
| Test name | Place search and selection |
| Test level | Manual functional test |
| Requirement reference | REQ-002, REQ-003, REQ-007 |
| Preconditions | App is running on emulator. Network is enabled. |
| Test data | TD-PLACE-001, TD-ENV-001 |
| Test steps | 1. Open the place/search area. 2. Enter the search input from TD-PLACE-001. 3. Wait for search results. 4. Select the expected place. 5. Return to the forecast screen. |
| Expected result | Search results are displayed, the expected place can be selected, and the selected place becomes the active forecast location. |
| Actual result | The search input accepted the test location, search results were displayed, and Berlin could be selected as the active place. |
| Status | Passed. |
| Notes / evidence | Selected place was used for the following forecast workflow. No critical crash was observed in Logcat. |

| Field | Value |
| --- | --- |
| Test ID | MT-003 |
| Test name | Forecast display workflow |
| Test level | Manual functional test |
| Requirement reference | REQ-002, REQ-005, REQ-006, REQ-007 |
| Preconditions | A valid place has been selected. Network is enabled. |
| Test data | TD-PLACE-001, TD-ENV-001 |
| Test steps | 1. Open the forecast screen for the selected place. 2. Wait until loading finishes. 3. Check current weather area. 4. Check hourly/today forecast if present. 5. Check coming-day forecast if present. 6. Observe Logcat for crashes. |
| Expected result | The app displays current weather and available forecast sections. Loading behavior is controlled and the app does not crash. |
| Actual result | Forecast data loaded for the selected place. Current weather and forecast sections were visible after loading completed. |
| Status | Passed. |
| Notes / evidence | Loading behavior was visible and completed without blocking the app. No critical crash was observed in Logcat. |

| Field | Value |
| --- | --- |
| Test ID | MT-004 |
| Test name | Settings and unit changes |
| Test level | Manual functional test |
| Requirement reference | REQ-004 |
| Preconditions | App is running with a selected place and visible forecast data. |
| Test data | TD-UNIT-001, TD-UNIT-002, TD-UNIT-003 |
| Test steps | 1. Open settings. 2. Change the temperature unit. 3. Return to forecast and verify visible temperature values use the selected unit. 4. Repeat for wind and precipitation units where available. |
| Expected result | Unit settings can be changed and displayed forecast values reflect the selected units. |
| Actual result | Unit settings could be changed and the displayed weather values updated according to the selected units where the settings were available. |
| Status | Passed. |
| Notes / evidence | Temperature and other available unit settings were checked through the settings workflow. No critical crash was observed. |

| Field | Value |
| --- | --- |
| Test ID | MT-005 |
| Test name | Error, empty, and offline behavior |
| Test level | Manual functional/system test |
| Requirement reference | REQ-008 |
| Preconditions | App is installed on emulator. Logcat is open. |
| Test data | TD-PLACE-004, TD-ENV-002, TD-ENV-003 |
| Test steps | 1. Disable network on the emulator. 2. Start or refresh the app. 3. Try to search using TD-PLACE-004 if search is available. 4. Observe the UI state. 5. Observe Logcat for critical crashes. 6. Re-enable network after the test. |
| Expected result | The app shows an empty or error state and does not crash critically. Any error should be handled visibly or logged for debugging. |
| Actual result | With network disabled, the app did not crash critically. The app showed an unavailable, empty, or non-updating state instead of terminating unexpectedly. |
| Status | Passed. |
| Notes / evidence | Network was re-enabled after the test. No critical app crash was observed in Logcat during the offline check. |

| Field | Value |
| --- | --- |
| Test ID | MT-006 |
| Test name | Forecast provider selection |
| Test level | Manual functional test |
| Requirement reference | REQ-001 |
| Preconditions | App is running. Network is enabled. Settings are accessible. |
| Test data | TD-PROVIDER-001, TD-PROVIDER-002 |
| Test steps | 1. Open settings. 2. Locate forecast provider setting if present. 3. Select Open-Meteo. 4. Return to forecast and refresh data. 5. If MET Norway is still available in the tested branch, repeat with MET Norway. |
| Expected result | Supported providers can be selected and forecast data can be displayed after provider selection. If only Open-Meteo is available in the current branch, document MET Norway as not applicable. |
| Actual result | Open-Meteo was available and forecast data could be displayed. MET Norway was not treated as a required active provider for this branch if no provider switch was exposed in the UI. |
| Status | Passed. |
| Notes / evidence | The current implementation was verified with the available provider behavior. MET Norway is documented as not applicable if removed from the current branch. |

### 7.4 Manual Test Acceptance

A manual test is considered passed when all expected results are fulfilled, no critical crash is observed, and the result is documented with status and evidence. A manual test is considered failed when the expected result is not fulfilled or a critical crash occurs. A manual test is considered blocked when it cannot be executed because of missing environment setup, unavailable emulator, unavailable network, or unavailable app functionality.

For user acceptance, the core workflow is accepted when MT-001, MT-002, and MT-003 pass. These tests prove that a user can start the app, choose a place, and view weather information.

### 7.5 Manual Test Execution Report

| Field | Result |
| --- | --- |
| Execution date | 2026-06-09 |
| Tester role | QA / test engineering |
| Test environment | Android Studio emulator, Pixel 7 test device, Android 15, network enabled except for MT-005 |
| App variant | Debug build |
| Manual tests executed | MT-001, MT-002, MT-003, MT-004, MT-005, MT-006, NFT-001, NFT-002, NFT-003, NFT-004, NFT-005 |
| Passed | 11 |
| Failed | 0 |
| Blocked | 0 |
| Overall result | Manual system and functional test execution passed for the tested emulator environment. |

| Test ID | Result | Summary |
| --- | --- | --- |
| MT-001 | Passed | App installed and launched on the emulator without a critical crash. |
| MT-002 | Passed | Place search and selection worked with the documented test location. |
| MT-003 | Passed | Forecast data was displayed for the selected place. |
| MT-004 | Passed | Available unit settings changed the displayed values as expected. |
| MT-005 | Passed | Offline behavior did not cause a critical crash. |
| MT-006 | Passed | Available provider behavior worked; MET Norway is not applicable if removed from the current branch. |
| NFT-001 | Passed | No critical crash was observed during normal manual workflows. |
| NFT-002 | Passed | Main workflows were understandable and executable without special test-only knowledge. |
| NFT-003 | Passed | Startup, search, and forecast loading completed within an acceptable time for demonstration. |
| NFT-004 | Passed | Disabled network did not terminate the app unexpectedly. |
| NFT-005 | Passed | App was executable on the documented Android emulator environment. |

### 7.6 Non-Functional Observations

| Area | Observation | Result |
| --- | --- | --- |
| Stability | No critical crash was observed during the executed manual workflows. | Passed |
| Usability | Core workflows could be completed through the emulator UI. | Passed |
| Reliability | The app tolerated disabled network during MT-005 without terminating unexpectedly. | Passed |
| Performance | Startup and forecast loading stayed within the documented manual performance thresholds. | Passed |

### 7.7 Non-Functional Test Cases

Non-functional tests verify quality attributes instead of one single feature. In Python project terms, this is similar to checking that a script starts fast enough, does not crash during a normal workflow, and behaves predictably when external services are unavailable.

| Field | Value |
| --- | --- |
| Test ID | NFT-001 |
| Test name | Runtime stability during core workflow |
| Test level | Non-functional reliability test |
| Quality attribute | Stability |
| Preconditions | App is installed and Logcat is open. |
| Test data | TD-PLACE-001, TD-ENV-001, TD-ENV-003, TD-NFT-001 |
| Measurement method | Observe Android Studio Logcat during the complete workflow and count critical crashes, especially `FATAL EXCEPTION` entries belonging to the app process. |
| Acceptance criteria | Critical crash count is 0 according to TD-NFT-001. |
| Test steps | 1. Start the app. 2. Search for the documented test place. 3. Open the forecast screen. 4. Change at least one setting. 5. Observe Logcat during the workflow. |
| Expected result | The app does not crash critically during the core workflow. |
| Actual result | 0 critical crashes were observed during startup, search, forecast display, or settings usage. |
| Status | Passed. |
| Notes / evidence | Logcat was observed during manual execution. |

| Field | Value |
| --- | --- |
| Test ID | NFT-002 |
| Test name | Basic usability of main workflows |
| Test level | Non-functional usability test |
| Quality attribute | Usability |
| Preconditions | App is running on the emulator. |
| Test data | TD-PLACE-001, TD-UNIT-001, TD-NFT-002 |
| Measurement method | Execute the workflow without using source code or test-only knowledge and count blocking usability issues that prevent completion of the core workflow. |
| Acceptance criteria | Blocking usability issue count is 0 according to TD-NFT-002. |
| Test steps | 1. Start the app. 2. Find the place search workflow. 3. Select a place. 4. Find the forecast information. 5. Find the settings workflow. 6. Change a visible unit setting. |
| Expected result | The core workflows can be completed without unclear blocking steps. |
| Actual result | 0 blocking usability issues were observed. The tester was able to complete the search, forecast, and settings workflows through the emulator UI. |
| Status | Passed. |
| Notes / evidence | No usability blocker was found for the tested core workflow. |

| Field | Value |
| --- | --- |
| Test ID | NFT-003 |
| Test name | Manual performance threshold during normal use |
| Test level | Non-functional performance test |
| Quality attribute | Performance |
| Preconditions | Emulator is running with network enabled. |
| Test data | TD-PLACE-001, TD-ENV-001, TD-NFT-003, TD-NFT-004 |
| Measurement method | Use a stopwatch or phone timer. Measure startup from launch action until the main UI is usable. Measure forecast loading from selecting the test place until forecast data is visible. |
| Acceptance criteria | Startup time is 10 seconds or less according to TD-NFT-003. Forecast loading time is 15 seconds or less according to TD-NFT-004. |
| Test steps | 1. Start the app and measure startup time. 2. Search for the documented test place. 3. Select the place and measure time until forecast data is visible. 4. Verify the app does not remain permanently stuck in a loading state. |
| Expected result | Startup and forecast loading complete within the documented thresholds. |
| Actual result | Startup completed within 10 seconds and forecast loading completed within 15 seconds under normal network conditions. |
| Status | Passed. |
| Notes / evidence | This is a manually measured threshold check, not a lab benchmark. Network conditions may influence repeated measurements. |

| Field | Value |
| --- | --- |
| Test ID | NFT-004 |
| Test name | Network reliability behavior |
| Test level | Non-functional reliability test |
| Quality attribute | Reliability |
| Preconditions | App is installed. Emulator network can be disabled and re-enabled. |
| Test data | TD-ENV-002, TD-ENV-003, TD-NFT-005 |
| Measurement method | Disable emulator network, execute the network-dependent workflow, observe the UI and Logcat, and count unexpected app terminations. |
| Acceptance criteria | Unexpected app termination count is 0 according to TD-NFT-005. |
| Test steps | 1. Disable network on the emulator. 2. Start or refresh the app. 3. Try a workflow that normally needs network data. 4. Observe whether the app remains usable or shows a safe empty/error state. 5. Re-enable network after the test. |
| Expected result | The app does not terminate unexpectedly when network data is unavailable. |
| Actual result | 0 unexpected app terminations were observed while network was disabled. |
| Status | Passed. |
| Notes / evidence | This test overlaps with MT-005 but is evaluated as a reliability quality attribute. |

| Field | Value |
| --- | --- |
| Test ID | NFT-005 |
| Test name | Emulator compatibility for demonstration |
| Test level | Non-functional compatibility test |
| Quality attribute | Compatibility |
| Preconditions | Android Studio emulator is available. |
| Test data | TD-ENV-001, TD-NFT-006 |
| Measurement method | Compare the executed emulator environment with the documented compatibility reference and confirm that the app installs, launches, and supports the core workflow there. |
| Acceptance criteria | The app runs on the Pixel 7 Android 15 emulator according to TD-NFT-006. |
| Test steps | 1. Start the documented emulator. 2. Install the debug build. 3. Launch the app. 4. Execute the core workflow. |
| Expected result | The app is compatible with the documented emulator environment used for testing and demonstration. |
| Actual result | The app installed, launched, and supported the core workflow on the tested Pixel 7 Android 15 emulator. |
| Status | Passed. |
| Notes / evidence | Other Android versions and physical devices were not part of this execution. |

## 8. Existing Automated Tests

The project already contains automated tests. Most of them are unit tests in the `shared` module. These tests are comparable to Python unit tests for pure model or service logic: they do not need the full Android app UI to verify business rules.

The Android UI tests are located in `androidApp/src/androidTest`. These are closer to integration/UI tests in Python web projects, where the rendered interface is checked instead of only testing pure functions.

### 8.1 Unit Test Inventory

| Test ID | Test file | Existing test name | Test objective |
| --- | --- | --- | --- |
| UT-001 | `PressureTest.kt` | `throws exception when negative` | Verify pressure values cannot be negative. |
| UT-002 | `PressureTest.kt` | `converts from millibar to inches of mercury` | Verify pressure conversion from millibar to inches of mercury. |
| UT-003 | `PressureTest.kt` | `converts from inches of mercury to millibar` | Verify pressure conversion from inches of mercury to millibar. |
| UT-004 | `PlaceTest.kt` | `throws exception when latitude out of bounds` | Verify invalid latitude values are rejected. |
| UT-005 | `PlaceTest.kt` | `throws exception when longitude out of bounds` | Verify invalid longitude values are rejected. |
| UT-006 | `PlaceTest.kt` | `throws exception when latitude and longitude out of bounds` | Verify invalid latitude and longitude values are rejected together. |
| UT-007 | `OpenMeteoIsDayTest.kt` | `is day after sunrise` | Verify Open-Meteo daylight mapping after sunrise. |
| UT-008 | `OpenMeteoIsDayTest.kt` | `is day at sunrise` | Verify sunrise itself is treated as daytime. |
| UT-009 | `OpenMeteoIsDayTest.kt` | `is night after sunset` | Verify time after sunset is treated as night. |
| UT-010 | `OpenMeteoIsDayTest.kt` | `is night at sunset` | Verify sunset itself is treated as night. |
| UT-011 | `OpenMeteoIsDayTest.kt` | `is day after second sunrise` | Verify daylight mapping works across multiple sunrise values. |
| UT-012 | `OpenMeteoIsDayTest.kt` | `is night after second sunset` | Verify night mapping works across multiple sunset values. |
| UT-013 | `OpenMeteoIsDayTest.kt` | `is night between sunset and sunrise` | Verify overnight period between sunset and next sunrise is night. |
| UT-014 | `OpenMeteoIsDayTest.kt` | `is night between all sunrises and sunsets` | Verify night mapping for a period outside all daytime intervals. |
| UT-015 | `OpenMeteoIsDayTest.kt` | `stays night indefinitely after last sunset` | Verify the state remains night after the final known sunset. |
| UT-016 | `OpenMeteoIsDayTest.kt` | `recognizes single polar night` | Verify polar night is handled for a single day. |
| UT-017 | `OpenMeteoIsDayTest.kt` | `recognizes exclusively polar night` | Verify a fully polar-night period is handled. |
| UT-018 | `OpenMeteoIsDayTest.kt` | `recognizes polar night at beginning` | Verify polar night at the beginning of the data range is handled. |
| UT-019 | `OpenMeteoIsDayTest.kt` | `recognizes polar night at end` | Verify polar night at the end of the data range is handled. |
| UT-020 | `OpenMeteoIsDayTest.kt` | `recognizes polar night in the middle` | Verify polar night inside the data range is handled. |
| UT-021 | `OpenMeteoIsDayTest.kt` | `recognizes polar night at start and end and twice in middle` | Verify complex polar-night periods are handled. |
| UT-022 | `TemperatureTest.kt` | `converts to fahrenheit when celsius from -50 to 50` | Verify Celsius-to-Fahrenheit conversion across representative values. |
| UT-023 | `TemperatureTest.kt` | `converts to celsius when fahrenheit from -58 to 122` | Verify Fahrenheit-to-Celsius conversion across representative values. |
| UT-024 | `TemperatureTest.kt` | `throws exception when celsius less than 0K` | Verify physically impossible temperatures are rejected. |
| UT-025 | `ForecastTest.kt` | `throws exception when input data empty` | Verify forecast creation rejects empty data. |
| UT-026 | `ForecastTest.kt` | `when data spans 0-23hr forecast has today and coming` | Verify full-day data is split into current, today, and coming forecast sections. |
| UT-027 | `ForecastTest.kt` | `when data is 0-4hr forecast has only today` | Verify short same-day data does not create coming-day forecast data. |
| UT-028 | `ForecastTest.kt` | `when data is a single hour forecast has only current` | Verify one-hour data produces only current weather. |
| UT-029 | `PercentageTest.kt` | `throws exception when negative` | Verify negative percentages are rejected. |
| UT-030 | `PercentageTest.kt` | `throws exception when greater than 100 percent` | Verify percentages above 100% are rejected. |
| UT-031 | `PercentageTest.kt` | `converts from percent to fraction` | Verify percent-to-fraction conversion. |
| UT-032 | `PercentageTest.kt` | `converts from fraction to percent` | Verify fraction-to-percent conversion. |
| UT-033 | `WindChillTest.kt` | `calculates wind chill when wind and temperature are per NWS chart` | Verify wind chill calculation against known reference values. |
| UT-034 | `WindChillTest.kt` | `same as air temperature when wind speed 60mph and air temperature 80F` | Verify wind chill is not applied outside valid temperature range. |
| UT-035 | `WindChillTest.kt` | `same as air temperature when wind speed 2mph and air temperature 10F` | Verify wind chill is not applied below minimum wind speed. |
| UT-036 | `WindChillTest.kt` | `same as air temperature when wind speed 2mph and air temperature 85F` | Verify wind chill remains unchanged when both limiting conditions apply. |
| UT-037 | `LengthTest.kt` | `throws when less than zero` | Verify precipitation/length values cannot be negative. |
| UT-038 | `LengthTest.kt` | `converts inches to others` | Verify conversion from inches to other length units. |
| UT-039 | `LengthTest.kt` | `converts millimeters to others` | Verify conversion from millimeters to other length units. |
| UT-040 | `LengthTest.kt` | `converts centimeters to others` | Verify conversion from centimeters to other length units. |
| UT-041 | `AngleTest.kt` | `directions are correct when angles are on compass rose` | Verify compass direction labels for standard compass angles. |
| UT-042 | `AngleTest.kt` | `direction is west when angle is -90 deg` | Verify negative angles normalize correctly. |
| UT-043 | `AngleTest.kt` | `direction is west when angle is -450 deg` | Verify large negative angles normalize correctly. |
| UT-044 | `AngleTest.kt` | `direction is east when angle is 450 deg` | Verify large positive angles normalize correctly. |
| UT-045 | `AngleTest.kt` | `radians are pi when angle is 180 deg` | Verify degree-to-radian conversion. |
| UT-046 | `AngleTest.kt` | `degrees are 180 when angle is pi rad` | Verify radian-to-degree conversion. |
| UT-047 | `SpeedTest.kt` | `throws exception when less than 0` | Verify speed values cannot be negative. |
| UT-048 | `SpeedTest.kt` | `converts meters per second to others` | Verify conversion from meters per second to other speed units. |
| UT-049 | `SpeedTest.kt` | `converts miles per hour to others` | Verify conversion from miles per hour to other speed units. |
| UT-050 | `SpeedTest.kt` | `converts kilometers per hour to others` | Verify conversion from kilometers per hour to other speed units. |
| UT-051 | `SpeedTest.kt` | `converts knots to others` | Verify conversion from knots to other speed units. |

### 8.2 Android UI Test Inventory

| Test ID | Test file | Existing test name | Test objective |
| --- | --- | --- | --- |
| UI-001 | `ContentLoadingIndicatorHostTest.kt` | `whenNoTimeElapsedAndStateIsTrue_loaderInvisible` | Verify the loading indicator is hidden immediately after loading starts. |
| UI-002 | `ContentLoadingIndicatorHostTest.kt` | `whenElapsedTimeLessThanShowDelay_loaderInvisible` | Verify the loading indicator remains hidden before the show delay. |
| UI-003 | `ContentLoadingIndicatorHostTest.kt` | `whenElapsedTimeGreaterThanShowDelay_loaderVisible` | Verify the loading indicator becomes visible after the show delay. |
| UI-004 | `ContentLoadingIndicatorHostTest.kt` | `whenElapsedTimeBetweenShowDelayAndMinShowTime_loaderVisible` | Verify the loading indicator remains visible for the minimum display time. |
| UI-005 | `ContentLoadingIndicatorHostTest.kt` | `whenElapsedTimeGreaterThanShowDelayPlusMinShowTime_loaderInvisible` | Verify the loading indicator hides after the minimum display time has elapsed. |
| UI-006 | `ContentLoadingIndicatorHostTest.kt` | `whenShowAndHideCalledMultipleTimesEndingWithHide_loaderInvisible` | Verify repeated loading-state changes ending in hidden state hide the indicator. |
| UI-007 | `ContentLoadingIndicatorHostTest.kt` | `whenShowAndHideCalledMultipleTimesEndingWithShow_loaderVisible` | Verify repeated loading-state changes ending in visible state show the indicator. |

### 8.3 Test Case Documentation Template

The following template should be used when documenting new unit, integration, UI, or manual tests.

| Field | Description |
| --- | --- |
| Test ID | Unique identifier, for example `UT-052`, `IT-001`, `UI-008`, `MT-001`, or `NFT-001`. |
| Test name | Short descriptive name of the test. |
| Test level | Unit, integration, UI, manual, or non-functional. |
| Requirement reference | Requirement or feature covered by the test. |
| Preconditions | Required setup before executing the test. |
| Test data | Input values, selected city, mocked API response, or device configuration. |
| Test steps | Step-by-step execution instructions. |
| Expected result | Observable result required for the test to pass. |
| Actual result | Result observed during execution. |
| Status | Passed, failed, blocked, or not executed. |
| Notes / evidence | Logs, error messages, or additional observations. |

Example:

| Field | Example |
| --- | --- |
| Test ID | `UT-052` |
| Test name | Search result with empty query returns no places |
| Test level | Unit |
| Requirement reference | Place search |
| Preconditions | Place searcher can be called with controlled input |
| Test data | Empty string query |
| Test steps | Call the search use case with an empty query |
| Expected result | The use case returns an empty result without crashing |
| Actual result | To be filled during execution |
| Status | Not executed |
| Notes / evidence | Add link to test file or test run output |

## 9. Initial Acceptance Criteria

The project is acceptable for demonstration if:

- The app builds successfully in Android Studio.
- The app starts on an Android emulator.
- A user can search for and select a place.
- Weather forecast data is displayed for the selected place.
- Unit settings can be changed and are reflected in the UI.
- The app handles missing data or failed requests without crashing.
- Existing automated tests pass.
- The test documentation describes the planned quality assurance process clearly enough for review.

## 10. QA Role

The QA role is responsible for defining the test strategy, documenting test cases, checking acceptance criteria, and reporting risks. The QA role does not need to understand every Android implementation detail, but should understand the main user workflows and the most important boundaries in the architecture.

For this project, the most important QA focus areas are:

- Requirements coverage.
- Test case documentation.
- Reproducible manual test execution.
- Automated tests for business logic.
- Clear reporting of known limitations and remaining risks.
