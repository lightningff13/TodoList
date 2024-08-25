plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.personal.todolist.data"
    compileSdk = 34

    defaultConfig {

        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))

    //Room + Kotlin Extensions and Coroutines support
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)


    //Hilt
    implementation(libs.bundles.hilt)

    testImplementation(project(":common"))
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.android.test)

    testFixturesCompileOnly(libs.bundles.coroutines)
    testFixturesCompileOnly(project(":common"))
    testFixturesCompileOnly(project(":domain"))
    testFixturesCompileOnly(libs.bundles.test)
}