# Manual Test Data

This file contains reusable manual test data for the documented manual functional tests. The goal is to keep concrete test data separate from the test procedure, similar to using fixtures or external test data files in a Python test project.

The Android instrumented UI tests were executed with:

./gradlew :androidApp:connectedDebugAndroidTest -Pandroid.experimental.androidTest.useUnifiedTestPlatform=false

Reason: the default Unified Test Platform runner aborted before test discovery because of a protobuf/ddmlib compatibility issue. With UTP disabled, all 7 connected UI tests executed successfully.

## Test Locations

| Data ID | Search input | Expected selection | Purpose |
| --- | --- | --- | --- |
| TD-PLACE-001 | `Berlin` | Berlin, Germany | Main positive test location for place search and forecast display. |
| TD-PLACE-002 | `Hamburg` | Hamburg, Germany | Secondary location for repeated search tests. |
| TD-PLACE-003 | `Osijek` | Osijek, Croatia | Useful existing reference because the repository already contains an Osijek test response resource. |
| TD-PLACE-004 | `zzzzzz-not-a-real-place` | No valid place expected | Negative test data for empty/no-result behavior. |

## Unit And Settings Values

| Data ID | Setting area | Values to verify | Purpose |
| --- | --- | --- | --- |
| TD-UNIT-001 | Temperature | Celsius, Fahrenheit | Verify that temperature unit changes are visible in the forecast UI. |
| TD-UNIT-002 | Wind speed | km/h, mph, m/s, knots, Beaufort where available | Verify that wind unit changes are visible in the forecast UI. |
| TD-UNIT-003 | Precipitation | millimetres, centimetres, inches where available | Verify that precipitation unit changes are visible in the forecast UI. |
| TD-UNIT-004 | Pressure | millibar, inches of mercury where available | Verify that pressure unit choices are available where the app exposes pressure settings. |

## Provider Values

| Data ID | Provider | Purpose |
| --- | --- | --- |
| TD-PROVIDER-001 | Open-Meteo | Verify that the Open-Meteo provider can be selected and used. |
| TD-PROVIDER-002 | MET Norway | Legacy/provider reference if still available in the current branch. If the app version under test has removed MET Norway, document this as not applicable. |

## Environment Values

| Data ID | Environment | Purpose |
| --- | --- | --- |
| TD-ENV-001 | Android Studio emulator with network enabled | Normal manual test execution. |
| TD-ENV-002 | Android Studio emulator with network disabled | Error/offline behavior testing. |
| TD-ENV-003 | Android Studio Logcat open during execution | Evidence that no critical crash appears during test execution. |

