// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.map.secret) apply false
}
buildscript {
    repositories {
        google()
    }
    dependencies {
        val nav_version = "2.8.4"
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}