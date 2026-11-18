/*
 * Copyright (c) 2023 mazziechai
 */

import dev.kordex.gradle.plugins.kordex.DataCollection

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")

    id("com.gradleup.shadow")

    id("dev.kordex.gradle.kordex")
    id("dev.kordex.gradle.i18n")

//    id("io.sentry.jvm.gradle") version "4.10.0"
}

group = "cafe.ferret"
version = "1.0"

repositories {
    maven {
        name = "Sonatype Snapshots (Legacy)"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }

    maven {
        name = "Sonatype Snapshots"
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kx.ser)

    // Logging dependencies
    implementation(libs.groovy)
    implementation(libs.jansi)
    implementation(libs.logback)
    implementation(libs.logging)

    implementation(libs.mongodb)
    implementation(libs.bson)
}

kordEx {
    // https://kordex.dev/blog/2024-07-23/kordex-2#levels
    bot {
        dataCollection(DataCollection.Standard)

        mainClass = "cafe.ferret.kosekata.AppKt"
    }

    module("dev-unsafe")
}

i18n {
    bundle("kose.strings", "cafe.ferret.kosekata.i18n")
}

tasks.compileTestJava {
    dependsOn(tasks.shadowJar)  // dumb fix
}

//sentry {
//    includeSourceContext = true
//
//    org = System.getenv("SENTRY_ORG")
//    projectName = "kose-kata"
//    authToken = System.getenv("SENTRY_AUTH_TOKEN")
//}
