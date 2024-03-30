plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation("com.google.code.gson:gson:2.9.1")

    // Room
    implementation("androidx.room:room-runtime:${property("room_version")}")
    kapt("androidx.room:room-compiler:${property("room_version")}")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:${property("retrofit_version")}")
    implementation("com.squareup.retrofit2:converter-gson:${property("retrofit_version")}")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:${property("hilt_version")}")
    kapt("com.google.dagger:hilt-android-compiler:${property("hilt_version")}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}