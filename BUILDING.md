# Building Prognoza

This document explains how to set up the build environment and how to build the Android app locally.

## Requirements

To build the project locally, you need:

- Android Studio
- JDK 17
- Android SDK installed
- Internet connection for the first Gradle sync

> Important: Use JDK 17 as the Gradle JDK.  
> Newer Java versions such as Java 21 can cause Gradle compatibility errors in this project.

## Project Structure

The most important build-related folders and files are:

```text
androidApp/                  Android application module
shared/                      Shared Kotlin Multiplatform code
buildSrc/                    Central place for versions and dependencies
gradle/wrapper/              Gradle Wrapper configuration
gradle.properties            Gradle and Android build settings
settings.gradle.kts          Project module configuration
build.gradle.kts             Project-level Gradle configuration