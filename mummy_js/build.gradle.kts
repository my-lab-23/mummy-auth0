plugins {
    kotlin("js") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.10"
}

group = "me.ema"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-RC3")
    implementation("io.ktor:ktor-client-js:1.6.5")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
    implementation("io.ktor:ktor-client-auth-basic:1.6.5")
}

kotlin {
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}