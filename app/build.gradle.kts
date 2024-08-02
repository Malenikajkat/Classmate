plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

}

android {
    namespace = "com.malenikajkat.classmate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.malenikajkat.classmate"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

    dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.constraintlayout)
        implementation(libs.androidx.lifecycle.livedata.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        implementation(libs.androidx.navigation.fragment.ktx)
        implementation(libs.androidx.navigation.ui.ktx)
        implementation(libs.firebase.database)
        implementation(libs.firebase.auth)
        implementation(libs.firebase.bom)
        implementation(libs.firebase.storage)
        implementation(libs.firebase.messaging)
        implementation(libs.volley)
        implementation(libs.desugar.jdk.libs)
        implementation(libs.picasso)
        implementation(libs.picasso.transformations)
        implementation(libs.play.services.ads.lite)
        implementation(libs.play.services.ads)
        implementation(libs.play.services.maps)
        implementation(libs.play.services.location)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        implementation(libs.google.services)


    }

