// Diese Datei enthält zentrale Build-Konfigurationswerte für das Projekt.
// Sie liegt im Ordner buildSrc. buildSrc ist ein spezieller Gradle-Ordner.
// Werte aus buildSrc können direkt in Gradle-Dateien wie build.gradle.kts verwendet werden.

// Diese Versionen wurden hier zentral ausgelagert, weil sie sowohl im
// projektweiten build.gradle.kts als auch bei den normalen Dependencies
// verwendet werden.

// Enthält globale Versionen für wichtige Build-Tools und Bibliotheken.
object GlobalVersions {

    // Kotlin-Version, mit der der Kotlin-Code des Projekts kompiliert wird.
    const val kotlin = "1.7.10"

    // Version des Compose Compilers.
    // Diese Version muss zur Kotlin-Version passen.
    // Wenn Kotlin aktualisiert wird, muss diese Version ebenfalls geprüft werden.
    // Kompatibilitätsübersicht:
    // https://developer.android.com/jetpack/androidx/releases/compose-kotlin#pre-release_kotlin_compatibility
    const val kotlinCompilerExtensionVersion = "1.3.1"

    // SQLDelight-Version für typsichere Datenbankzugriffe.
    const val sqlDelight = "1.5.3"

    // Version des Android Gradle Plugins.
    // Diese Version sollte nicht ohne Kompatibilitätsprüfung geändert werden.
    // Wichtig sind dabei: Gradle Wrapper, Kotlin-Version, Android Studio und JDK-Version.
    const val gradle = "7.2.2"

    // Hilt-Version für Dependency Injection.
    const val hilt = "2.43.2"
}

// Enthält zentrale Android-SDK- und JVM-Konfigurationen für das Projekt.
object AndroidConfig {

    // Niedrigste Android-Version, auf der die App läuft.
    // API 26 entspricht Android 8.0.
    const val minSdk = 26

    // Android-SDK-Version, gegen die die App kompiliert wird.
    // API 33 entspricht Android 13.
    const val compileSdk = 33

    // Ziel-SDK-Version der App.
    // Hier wird sie automatisch auf denselben Wert wie compileSdk gesetzt.
    const val targetSdk = compileSdk

    // JVM-Zielversion für den Kotlin-/Java-Bytecode.
    // Das bedeutet nicht, dass Gradle selbst mit Java 11 läuft.
    // Für den Gradle-Build soll in diesem Projekt JDK 17 verwendet werden.
    const val jvmTarget = "11"
}

// Enthält alle externen Bibliotheken, die im Projekt verwendet werden.
// Diese Konstanten werden in Gradle-Dateien referenziert.
object Dependencies {

    // JUnit wird für lokale Unit Tests verwendet.
    // Diese Tests laufen auf dem Rechner ohne Emulator.
    const val jUnit = "junit:junit:4.13"

    // Napier ist eine Logging-Bibliothek für Log-Ausgaben in der App.
    const val napier = "io.github.aakira:napier:2.6.1"

    // Dependencies für Kotlin Serialization.
    // Wird verwendet, um Kotlin-Objekte in JSON umzuwandeln
    // und JSON wieder in Kotlin-Objekte zurückzuwandeln.
    object Serialization {
        private const val version = "1.4.1"

        // Grundbibliothek für Serialization.
        const val core = "org.jetbrains.kotlinx:kotlinx-serialization-core:$version"

        // JSON-Unterstützung für Kotlin Serialization.
        const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:$version"
    }

    // Ktor-Dependencies.
    // Ktor wird als HTTP-Client für Netzwerkkommunikation verwendet.
    object Ktor {
        private const val version = "2.1.3"

        // Grundmodul des Ktor Clients.
        const val core = "io.ktor:ktor-client-core:$version"

        // Android-spezifische Ktor Client Engine.
        const val android = "io.ktor:ktor-client-android:$version"

        // Darwin Engine für Apple-/native Plattformen.
        // Relevant, weil das Projekt auch gemeinsamen Multiplatform-Code enthält.
        const val darwin = "io.ktor:ktor-client-darwin:$version"

        // CIO Engine für JVM-/Netzwerkoperationen.
        const val cio = "io.ktor:ktor-client-cio:$version"

        // Logging-Unterstützung für Ktor-Anfragen und Antworten.
        const val logging = "io.ktor:ktor-client-logging:$version"

        // Unterstützt automatische Verarbeitung von Antwortformaten wie JSON.
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"

        // Verbindung zwischen Ktor und Kotlin Serialization.
        const val serialization = "io.ktor:ktor-serialization-kotlinx-json:$version"
    }

    // SQLDelight-Dependencies.
    // SQLDelight erzeugt typsicheren Kotlin-Code aus SQL-Dateien.
    object SqlDelight {
        private const val version = GlobalVersions.sqlDelight

        // SQLDelight Runtime-Bibliothek.
        const val core = "com.squareup.sqldelight:runtime:$version"

        // Android SQLite-Treiber für SQLDelight.
        const val android = "com.squareup.sqldelight:android-driver:$version"

        // Native SQLDelight-Treiber für Multiplatform-/native Codebereiche.
        const val native = "com.squareup.sqldelight:native-driver:$version"
    }

    // Kotlin Coroutines Dependencies.
    // Coroutines werden für asynchrone Aufgaben verwendet,
    // z. B. Netzwerkaufrufe, Datenbankzugriffe oder Hintergrundarbeit.
    object Coroutines {
        private const val version = "1.6.1"

        // Android-spezifische Coroutine-Unterstützung,
        // z. B. für den Main Dispatcher.
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"

        // Grundbibliothek für Coroutines.
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    }

    // Kotlinx DateTime Dependencies.
    // Wird für Datum, Uhrzeit und Zeitberechnungen verwendet.
    object DateTime {
        private const val version = "0.4.0"

        // JVM-spezifische DateTime-Bibliothek.
        const val jvm = "org.jetbrains.kotlinx:kotlinx-datetime-jvm:$version"

        // Gemeinsame DateTime-Bibliothek für shared/multiplatform Code.
        const val core = "org.jetbrains.kotlinx:kotlinx-datetime:$version"
    }

    // Android- und Jetpack-Dependencies für das Android-App-Modul.
    object Android {

        // Android KTX-Erweiterungen für angenehmere Android-APIs in Kotlin.
        const val core = "androidx.core:core-ktx:1.7.0"

        // AppCompat-Bibliothek für Kompatibilität mit älteren Android-Versionen.
        const val appcompat = "androidx.appcompat:appcompat:1.5.1"

        // Splash Screen API für den Startbildschirm der App.
        const val splashScreen = "androidx.core:core-splashscreen:1.0.0"

        // WorkManager für zuverlässige Hintergrundaufgaben.
        const val work = "androidx.work:work-runtime-ktx:2.7.1"

        // Glance AppWidget-Bibliothek für Android-Startbildschirm-Widgets.
        const val glance = "androidx.glance:glance-appwidget:1.0.0-alpha05"
        const val location = "com.google.android.gms:play-services-location:21.0.1"

        // Hilt-Dependencies für Dependency Injection.
        object Hilt {
            private const val version = GlobalVersions.hilt

            // Hauptbibliothek von Hilt für Android.
            const val core = "com.google.dagger:hilt-android:$version"

            // Hilt Compiler.
            // Wird von kapt benutzt, um Dependency-Injection-Code beim Build zu generieren.
            const val kapt = "com.google.dagger:hilt-compiler:$version"

            // Hilt-Integration für Jetpack Compose Navigation.
            const val composeNavigation = "androidx.hilt:hilt-navigation-compose:1.0.0"
        }

        // Android Lifecycle Dependencies.
        // Werden für lifecycle-aware Komponenten und ViewModels verwendet.
        object Lifecycle {
            private const val version = "2.5.1"

            // Lifecycle Runtime mit Kotlin-Erweiterungen.
            const val core = "androidx.lifecycle:lifecycle-runtime-ktx:$version"

            // ViewModel-Unterstützung für Kotlin.
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"

            // ViewModel-Integration für Jetpack Compose.
            const val composeViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
        }

        // Jetpack Compose Dependencies.
        // Compose ist das moderne Android-UI-Framework.
        object Compose {
            private const val version = "1.3.1"

            // Grundbibliothek für Compose UI.
            const val core = "androidx.compose.ui:ui:$version"

            // Unterstützung für Animationen in Compose.
            const val animation = "androidx.compose.animation:animation:$version"

            // Tooling für Previews und Debugging in Android Studio.
            const val tooling = "androidx.compose.ui:ui-tooling:$version"

            // Testbibliothek für Compose UI Tests.
            const val testJUnit4 = "androidx.compose.ui:ui-test-junit4:$version"

            // Manifest-Unterstützung für Compose UI Tests.
            const val testManifest = "androidx.compose.ui:ui-test-manifest:$version"

            // Compose-Integration für Android Activity.
            const val activity = "androidx.activity:activity-compose:1.6.1"

            // Material 3 Komponenten für Compose UI.
            const val material3 = "androidx.compose.material3:material3:1.0.0"

            // Navigation zwischen Compose-Screens.
            const val navigation = "androidx.navigation:navigation-compose:2.5.3"

            // Coil-Bibliothek zum Laden von Bildern in Compose.
            const val coil = "io.coil-kt:coil-compose:2.1.0"

            // Accompanist System UI Controller zur Steuerung von Systemleisten.
            const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:0.27.0"
        }

        // Android-Test-Dependencies.
        // Diese werden hauptsächlich für Instrumentation Tests und UI Tests
        // auf Emulatoren oder echten Geräten verwendet.
        object Test {
            private const val version = "1.4.0"

            // AndroidX Test Core-Bibliothek.
            const val core = "androidx.test:core:$version"

            // Android Instrumentation Test Runner.
            const val runner = "androidx.test:runner:$version"

            // Android Test Rules.
            const val rules = "androidx.test:rules:$version"

            // AndroidX JUnit-Erweiterung.
            const val androidJUnit = "androidx.test.ext:junit:1.1.2"

            // Espresso Framework für UI Tests.
            const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        }
    }
}