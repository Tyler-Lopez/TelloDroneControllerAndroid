// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.hiltAndroidGradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}