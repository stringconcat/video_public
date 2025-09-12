plugins {
    alias(libs.plugins.jvm)
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("io.arrow-kt:arrow-core:2.0.1")

    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)
    testImplementation ("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation ("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation ("io.kotest:kotest-property:5.9.1")
    testImplementation ("org.graalvm.js:js:22.3.0")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
