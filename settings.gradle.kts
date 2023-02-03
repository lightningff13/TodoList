pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            version("androidx.compose", "1.3.1")
            version("compose.ui", "1.3.1")
            version("nav", "2.4.1")
            version("lifecycle", "2.5.1")
            version("lifecycle.runtime.compose","2.6.0-alpha04" )
            version("room", "2.4.3")
            version("mockK", "1.12.0")
            version("coroutines", "1.6.4")
            version("hilt", "2.42")
            version("hilt.compiler", "1.0.0")
            version("truth", "1.1")
            version("core.ktx", "1.9.0")

            library("androidx.lifecycle.lifecycle.runtime.ktx", "androidx.lifecycle", "lifecycle-runtime-ktx").versionRef("lifecycle")
            library("androidx.lifecycle.lifecycle.runtime.compose", "androidx.lifecycle", "lifecycle-runtime-compose").versionRef("lifecycle.runtime.compose")

            library("androidx.activity.compose", "androidx.activity:activity-compose:1.6.1")
            library("androidx.compose.ui", "androidx.compose.ui", "ui").versionRef("androidx.compose")
            library("androidx.compose.ui.tooling.preview", "androidx.compose.ui", "ui-tooling-preview").versionRef("androidx.compose")
            library("androidx.compose.material", "androidx.compose.material", "material").versionRef("androidx.compose")
            library("androidx.navigation", "androidx.navigation:navigation-compose:2.5.3")
            library("accompanist.navigation", "com.google.accompanist:accompanist-navigation-animation:0.29.1-alpha")

            bundle("compose", listOf("androidx.activity.compose", "androidx.compose.ui", "androidx.compose.ui.tooling.preview", "androidx.compose.material", "androidx.navigation", "accompanist.navigation"))

            library("androidx.compose.ui.tooling", "androidx.compose.ui", "ui-tooling").versionRef("androidx.compose")
            library("androidx.compose.ui.test.manifest", "androidx.compose.ui", "ui-test-manifest").versionRef("androidx.compose")

            bundle("compose.debug", listOf("androidx.compose.ui.tooling", "androidx.compose.ui.test.manifest"))

            library("androidx.core", "androidx.core","core-ktx").versionRef("core-ktx")

            library("kotlinx.coroutines.core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("coroutines")
            library("kotlinx.coroutines.android", "org.jetbrains.kotlinx", "kotlinx-coroutines-android").versionRef("coroutines")
            bundle("coroutines", listOf("kotlinx.coroutines.core", "kotlinx.coroutines.android"))

            library("lifecycle.viewmodel.ktx", "androidx.lifecycle", "lifecycle-viewmodel-ktx").versionRef("lifecycle")
            library("lifecycle.viewmodel.compose", "androidx.lifecycle", "lifecycle-viewmodel-compose").versionRef("lifecycle")
            library("lifecycle.common", "androidx.lifecycle", "lifecycle-common-java8").versionRef("lifecycle")
            bundle("lifecycle", listOf("lifecycle.viewmodel.ktx", "lifecycle.viewmodel.compose", "lifecycle.common"))

            library("room.runtime", "androidx.room", "room-runtime").versionRef("room")
            library("room.ktx", "androidx.room", "room-ktx").versionRef("room")
            library("room.compiler", "androidx.room", "room-compiler").versionRef("room")

            bundle("room", listOf("room.runtime", "room.ktx"))

            library("hilt.android", "com.google.dagger", "hilt-android").versionRef("hilt")
            plugin("hiltcompiler", "com.google.dagger:hilt-android-compiler").versionRef("hilt")
            library("hilt.navigation", "androidx.hilt:hilt-navigation-compose:1.0.0")

            bundle("hilt", listOf("hilt.android", "hilt.navigation"))

            library("room.testing", "androidx.room", "room-testing").versionRef("room")
            library("junit.jupiter", "org.junit.jupiter:junit-jupiter:5.8.2")
            library("google.truth", "com.google.truth", "truth").versionRef("truth")
            library("mockk", "io.mockk", "mockk").versionRef("mockK")
            library("kotlinx.test.core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("coroutines")
            library("kotlinx.test", "org.jetbrains.kotlinx", "kotlinx-coroutines-test").versionRef("coroutines")
            library("android.arch.core.test", "android.arch.core:core-testing:1.1.0")

            bundle("test", listOf("room.testing", "junit.jupiter", "google.truth", "mockk", "kotlinx.test.core", "kotlinx.test", "android.arch.core.test"))

            library("junit", "androidx.test.ext:junit:1.1.5")
            library("expresso.core", "androidx.test.espresso:espresso-core:3.5.1")
            library("androidx.compose.ui.test.junit", "androidx.compose.ui", "ui-test-junit4").versionRef("androidx.compose")

            bundle("android.test", listOf("junit", "google.truth", "android.arch.core.test", "kotlinx.test.core", "expresso.core", "androidx.compose.ui.test.junit"))
        }
    }
}

include(":app")
rootProject.name = "TodoList"