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

## 7. Existing Automated Tests

The project already contains automated tests. Most of them are unit tests in the `shared` module. These tests are comparable to Python unit tests for pure model or service logic: they do not need the full Android app UI to verify business rules.

The Android UI tests are located in `androidApp/src/androidTest`. These are closer to integration/UI tests in Python web projects, where the rendered interface is checked instead of only testing pure functions.

### 7.1 Unit Test Inventory

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

### 7.2 Android UI Test Inventory

| Test ID | Test file | Existing test name | Test objective |
| --- | --- | --- | --- |
| UI-001 | `ContentLoadingIndicatorHostTest.kt` | `whenNoTimeElapsedAndStateIsTrue_loaderInvisible` | Verify the loading indicator is hidden immediately after loading starts. |
| UI-002 | `ContentLoadingIndicatorHostTest.kt` | `whenElapsedTimeLessThanShowDelay_loaderInvisible` | Verify the loading indicator remains hidden before the show delay. |
| UI-003 | `ContentLoadingIndicatorHostTest.kt` | `whenElapsedTimeGreaterThanShowDelay_loaderVisible` | Verify the loading indicator becomes visible after the show delay. |
| UI-004 | `ContentLoadingIndicatorHostTest.kt` | `whenElapsedTimeBetweenShowDelayAndMinShowTime_loaderVisible` | Verify the loading indicator remains visible for the minimum display time. |
| UI-005 | `ContentLoadingIndicatorHostTest.kt` | `whenElapsedTimeGreaterThanShowDelayPlusMinShowTime_loaderInvisible` | Verify the loading indicator hides after the minimum display time has elapsed. |
| UI-006 | `ContentLoadingIndicatorHostTest.kt` | `whenShowAndHideCalledMultipleTimesEndingWithHide_loaderInvisible` | Verify repeated loading-state changes ending in hidden state hide the indicator. |
| UI-007 | `ContentLoadingIndicatorHostTest.kt` | `whenShowAndHideCalledMultipleTimesEndingWithShow_loaderVisible` | Verify repeated loading-state changes ending in visible state show the indicator. |

### 7.3 Test Case Documentation Template

The following template should be used when documenting new unit, integration, UI, or manual tests.

| Field | Description |
| --- | --- |
| Test ID | Unique identifier, for example `UT-052`, `IT-001`, `UI-008`, or `MT-001`. |
| Test name | Short descriptive name of the test. |
| Test level | Unit, integration, UI, manual, or non-functional. |
| Requirement reference | Requirement or feature covered by the test. |
| Preconditions | Required setup before executing the test. |
| Test data | Input values, selected city, mocked API response, or device configuration. |
| Test steps | Step-by-step execution instructions. |
| Expected result | Observable result required for the test to pass. |
| Actual result | Result observed during execution. |
| Status | Passed, failed, blocked, or not executed. |
| Notes / evidence | Screenshots, logs, error messages, or additional observations. |

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

## 8. Initial Acceptance Criteria

The project is acceptable for demonstration if:

- The app builds successfully in Android Studio.
- The app starts on an Android emulator.
- A user can search for and select a place.
- Weather forecast data is displayed for the selected place.
- Unit settings can be changed and are reflected in the UI.
- The app handles missing data or failed requests without crashing.
- Existing automated tests pass.
- The test documentation describes the planned quality assurance process clearly enough for review.

## 9. QA Role

The QA role is responsible for defining the test strategy, documenting test cases, checking acceptance criteria, and reporting risks. The QA role does not need to understand every Android implementation detail, but should understand the main user workflows and the most important boundaries in the architecture.

For this project, the most important QA focus areas are:

- Requirements coverage.
- Test case documentation.
- Reproducible manual test execution.
- Automated tests for business logic.
- Clear reporting of known limitations and remaining risks.
