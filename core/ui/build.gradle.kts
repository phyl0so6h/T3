plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.sahak7an.t3.core.ui"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    api(project(":core:model"))
    api(libs.androidx.recyclerview)
    api(libs.google.material)
    api(libs.adapterdelegates4)
    api(libs.adapterdelegates4.kotlin.dsl.viewbinding)
    api(libs.androidx.cardview)
}

