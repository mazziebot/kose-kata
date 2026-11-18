/*
 * Copyright (c) 2023 mazziechai
 */

pluginManagement {
    plugins {
        // Update this in libs.version.toml when you change it here
        kotlin("jvm") version "2.3.10"
        kotlin("plugin.serialization") version "2.3.10"

        id("com.gradleup.shadow") version "9.6.1"

        id("dev.kordex.gradle.kordex") version "1.9.2"
        id("dev.kordex.gradle.i18n") version "1.1.1"

    }

    repositories {
        gradlePluginPortal()
        mavenCentral()

        maven("https://snapshots-repo.kordex.dev")
        maven("https://releases-repo.kordex.dev")
    }
}

rootProject.name = "kose-kata"
