// build.gradle (Nivel de Aplicación)
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    alias(libs.plugins.google.gms.google.services)
    kotlin("kapt") // Habilita KAPT para el procesamiento de anotaciones
}

android {
    namespace = "com.example.snaphunters"
    compileSdk = 34 // Cambia a la versión que necesites

    defaultConfig {
        applicationId = "com.example.snaphunters"
        minSdk = 24 // Cambia según tus requisitos
        targetSdk = 34 // Cambia a la versión que necesites
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Cambia a true si usas ProGuard
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true // Habilita ViewBinding
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Compatibilidad con Java 1.8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8" // Establece el objetivo de la JVM a 1.8
    }
}

dependencies {
    implementation("com.github.bumptech.glide:glide:4.15.0") // Dependencia para cargar imágenes
    kapt("com.github.bumptech.glide:compiler:4.15.0") // Procesador de anotaciones para Glide
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0") // Almacenamiento en Firebase
    implementation("com.google.firebase:firebase-auth-ktx:22.1.0") // Autenticación en Firebase
    implementation(libs.androidx.core.ktx) // KTX para funciones de extensión
    implementation(libs.androidx.appcompat) // Compatibilidad con versiones anteriores de Android
    implementation(libs.material) // Material Design
    implementation(libs.androidx.activity) // Componentes de actividad
    implementation(libs.androidx.constraintlayout) // Layouts de Constraint
    implementation(libs.play.services.maps) // Google Maps
    implementation("androidx.biometric:biometric:1.1.0") // Agrega esta línea para Biometric
    testImplementation(libs.junit) // Dependencia para pruebas unitarias
    androidTestImplementation(libs.androidx.junit) // Dependencia para pruebas de Android
    androidTestImplementation(libs.androidx.espresso.core) // Dependencia para pruebas de UI
}
