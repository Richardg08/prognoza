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

## 6. Existing Automated Tests

The project already contains automated tests. Most of them are unit tests in the `shared` module. These tests are comparable to Python unit tests for pure model or service logic: they do not need the full Android app UI to verify business rules.

The Android UI tests are located in `androidApp/src/androidTest`. These are closer to integration/UI tests in Python web projects, where the rendered interface is checked instead of only testing pure functions.

### 6.1 Unit Test Inventory

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

### 6.2 Android UI Test Inventory

| Test ID | Test file | Existing test name | Test objective |
| --- | --- | --- | --- |
| UI-001 | `ContentLoadingIndicatorHostTest.kt` | `whenNoTimeElapsedAndStateIsTrue_loaderInvisible` | Verify the loading indicator is hidden immediately after loading starts. |
| UI-002 | `ContentLoadingIndicatorHostTest.kt` | `whenElapsedTimeLessThanShowDelay_loaderInvisible` | Verify the loading indicator remains hidden before the show delay. |
| UI-003 | `ContentLoadingIndicatorHostTest.kt` | `whenElapsedTimeGreaterThanShowDelay_loaderVisible` | Verify the loading indicator becomes visible after the show delay. |
| UI-004 | `ContentLoadingIndicatorHostTest.kt` | `whenElapsedTimeBetweenShowDelayAndMinShowTime_loaderVisible` | Verify the loading indicator remains visible for the minimum display time. |
| UI-005 | `ContentLoadingIndicatorHostTest.kt` | `whenElapsedTimeGreaterThanShowDelayPlusMinShowTime_loaderInvisible` | Verify the loading indicator hides after the minimum display time has elapsed. |
| UI-006 | `ContentLoadingIndicatorHostTest.kt` | `whenShowAndHideCalledMultipleTimesEndingWithHide_loaderInvisible` | Verify repeated loading-state changes ending in hidden state hide the indicator. |
| UI-007 | `ContentLoadingIndicatorHostTest.kt` | `whenShowAndHideCalledMultipleTimesEndingWithShow_loaderVisible` | Verify repeated loading-state changes ending in visible state show the indicator. |

### 6.3 Test Case Documentation Template

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

## 7. Initial Acceptance Criteria

The project is acceptable for demonstration if:

- The app builds successfully in Android Studio.
- The app starts on an Android emulator.
- A user can search for and select a place.
- Weather forecast data is displayed for the selected place.
- Unit settings can be changed and are reflected in the UI.
- The app handles missing data or failed requests without crashing.
- Existing automated tests pass.
- The test documentation describes the planned quality assurance process clearly enough for review.

## 8. QA Role

The QA role is responsible for defining the test strategy, documenting test cases, checking acceptance criteria, and reporting risks. The QA role does not need to understand every Android implementation detail, but should understand the main user workflows and the most important boundaries in the architecture.

For this project, the most important QA focus areas are:

- Requirements coverage.
- Test case documentation.
- Reproducible manual test execution.
- Automated tests for business logic.
- Clear reporting of known limitations and remaining risks.
