buildscript {
    val kotlin_version: String by extra("1.7.20")
    val room_version: String by extra("2.5.0")
    val koin_version: String by extra("2.0.1")
    val lifecycle_version: String by extra("2.2.0")
    val retrofit_version: String by extra("2.9.0")
    val hilt_version: String by extra("2.44")
    val nav_version: String by extra("2.5.3")

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hilt_version")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")


    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}