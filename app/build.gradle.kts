plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 33
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.personal.todolist"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.lifecycle.runtime.ktx)
    implementation(libs.bundles.compose)
    implementation("com.google.android.material:material:1.7.0")

    implementation(libs.androidx.core)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.lifecycle)

    debugImplementation(libs.bundles.compose.debug)

    //Room + Kotlin Extensions and Coroutines support
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    //Hilt
    implementation(libs.bundles.hilt)
    kapt("com.google.dagger:hilt-android-compiler:2.42")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.android.test)
}