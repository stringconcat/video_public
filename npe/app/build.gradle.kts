import org.gradle.api.tasks.compile.JavaCompile
import net.ltgt.gradle.errorprone.errorprone

plugins {
    application
    id("net.ltgt.errorprone") version "4.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.jspecify)

    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    errorprone(libs.errorprone.core)
    errorprone(libs.nullaway)

    implementation(libs.guava)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "org.example.App"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile>().configureEach {
    options.errorprone {
        disableAllChecks = true
        option("NullAway:OnlyNullMarked", "true")
        option("NullAway:JSpecifyMode", "true")
        error("NullAway")
    }
}
