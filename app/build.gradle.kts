plugins {
    id("com.android.application")
    id("kotlin-kapt")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    compileSdk = 34

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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.personal.todolist"
}

dependencies {
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.bundles.compose)
    implementation("com.google.android.material:material:1.7.0")

    implementation(libs.androidx.core)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.lifecycle)

    debugImplementation(libs.bundles.compose.debug)

    //Room + Kotlin Extensions and Coroutines support
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    //Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)

    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.android.test)
}