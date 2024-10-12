plugins {
    id("com.android.application")
    id("kotlin-kapt")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
}
detekt {
    ignoreFailures = true
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

    buildFeatures {
        buildConfig = true
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
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":common"))

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.bundles.compose)
    implementation(libs.material)

    implementation(libs.androidx.core)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.lifecycle)

    debugImplementation(libs.bundles.compose.debug)

    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    //Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)

    testImplementation(testFixtures(project(":data")))
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.android.test)
}