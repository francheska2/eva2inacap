plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    // 💡 1. APLICAR EL PLUGIN DE GOOGLE SERVICES
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.eva2inacap"
    compileSdk = 36 // Se mantiene como estaba

    defaultConfig {
        applicationId = "com.example.eva2inacap"
        minSdk = 24
        targetSdk = 36
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    // --- DEPENDENCIAS EXISTENTES ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // --- DEPENDENCIAS DE FIREBASE ---

    // 💡 2. IMPORTAR LA PLATAFORMA (BoM) para gestionar todas las versiones de Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.0.0")) // Verifica la versión más reciente en Firebase

    // 💡 3. AÑADIR LA LIBRERÍA BASE (Analytics)
    // Esto conecta la app a Firebase y la inicializa.
    implementation("com.google.firebase:firebase-analytics-ktx")
}