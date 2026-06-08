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

## 6. Initial Acceptance Criteria

The project is acceptable for demonstration if:

- The app builds successfully in Android Studio.
- The app starts on an Android emulator.
- A user can search for and select a place.
- Weather forecast data is displayed for the selected place.
- Unit settings can be changed and are reflected in the UI.
- The app handles missing data or failed requests without crashing.
- Existing automated tests pass.
- The test documentation describes the planned quality assurance process clearly enough for review.

## 7. QA Role

The QA role is responsible for defining the test strategy, documenting test cases, checking acceptance criteria, and reporting risks. The QA role does not need to understand every Android implementation detail, but should understand the main user workflows and the most important boundaries in the architecture.

For this project, the most important QA focus areas are:

- Requirements coverage.
- Test case documentation.
- Reproducible manual test execution.
- Automated tests for business logic.
- Clear reporting of known limitations and remaining risks.
