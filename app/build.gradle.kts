plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.ynov.myrecipes"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ynov.myrecipes"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions { jvmTarget = "11" }

    buildFeatures { viewBinding = true }
}

dependencies {

    configurations.configureEach {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin") {
                when (requested.name) {
                    "kotlin-stdlib",
                    "kotlin-stdlib-jdk7",
                    "kotlin-stdlib-jdk8",
                    "kotlin-stdlib-common",
                    "kotlin-reflect"      -> useVersion("1.9.23")
                }
            }
        }
    }

    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // --- Core / UI
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    // --- Navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // --- Network
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.logging.interceptor)
    implementation(libs.moshi.kotlin)

    // --- Coroutines & Lifecycle
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    // --- Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // --- Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // --- Glide
    implementation(libs.glide)
    kapt(libs.glide.compiler)

    implementation(libs.moshi.kotlin)
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    // --- Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
}
