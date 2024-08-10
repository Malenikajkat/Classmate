plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.malenikajkat.classmate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.malenikajkat.classmate"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")

    // Firebase зависимости для работы с базой данных, аутентификацией, хранилищем и уведомлениями
    implementation(platform("com.google.firebase:firebase-bom:28.3.0"))
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-messaging")

    // Зависимость для работы с HTTP-запросами
    implementation("com.android.volley:volley:1.2.1")

    // Материальные компоненты от Google
    implementation("com.google.android.material:material:1.5.0")

    // Picasso для загрузки изображений
    implementation("com.squareup.picasso:picasso:2.71828")

    // Google Play Services для рекламы, карты и определения местоположения
    implementation("com.google.android.gms:play-services-ads-lite:20.3.0")
    implementation("com.google.android.gms:play-services-ads:20.3.0")
    implementation("com.google.android.gms:play-services-maps:18.0.2")
    implementation("com.google.android.gms:play-services-location:19.0.1")

    // Заменяем текущие зависимости kotlin-stdlib на последнюю версию kotlin-stdlib-jdk8
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22")

    // Убедитесь, что никаких версий stdlib-jdk7 не осталось
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.22") {
        exclude(module = "kotlin-stdlib-jdk7")
    }

    // Kotlin Coroutines для асинхронного программирования
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")

    // Дополнительные зависимости AndroidX
    implementation("androidx.lifecycle:lifecycle-common:2.4.0")
    implementation("androidx.fragment:fragment:1.3.6")

    // Зависимости для тестирования
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Retrofit и OkHttp для работы с REST API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // Добавляем правильную версию библиотеки Guava
    implementation("com.google.guava:guava:30.0-jre")
}