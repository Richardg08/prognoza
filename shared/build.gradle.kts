plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

kotlin {
    android()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.napier)
                implementation(Dependencies.DateTime.core)
                implementation(Dependencies.Coroutines.core)
                implementation(Dependencies.SqlDelight.core)
                implementation(Dependencies.Serialization.json)
                implementation(Dependencies.Ktor.core)
                implementation(Dependencies.Ktor.cio)
                implementation(Dependencies.Ktor.logging)
                implementation(Dependencies.Ktor.contentNegotiation)
                implementation(Dependencies.Ktor.serialization)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.SqlDelight.android)
                implementation(Dependencies.Ktor.android)
            }
        }
        val androidTest by getting
    }
}

android {
    namespace = "hr.dtakac.prognoza.shared"
    compileSdk = AndroidConfig.compileSdk
    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
    }
}

sqldelight {
    database("PrognozaDatabase") {
        packageName = "hr.dtakac.prognoza.shared.data"
    }
}
