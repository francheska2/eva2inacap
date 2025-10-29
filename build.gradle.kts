// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    // 💡 AÑADIR ESTA LÍNEA para habilitar el plugin de Google Services (Firebase)
    id("com.google.gms.google-services") version "4.4.1" apply false // (Verifica la versión más reciente)
}