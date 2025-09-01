plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}
kotlin {
    jvmToolchain(11)
}
android {
    namespace = "com.personal.todolist.domain"
    compileSdk = 36

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":common"))

    implementation(libs.bundles.coroutines)
    //Hilt
    implementation(libs.bundles.hilt)

    testImplementation(libs.bundles.test)
    testImplementation(testFixtures(project(":data")))
    testImplementation(project(":data"))
}