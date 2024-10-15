// build.gradle (Nivel del Proyecto)
// Archivo de construcción a nivel superior donde puedes agregar opciones de configuración comunes a todos los subproyectos/módulos.
plugins {
    alias(libs.plugins.android.application) apply false // Plugin de Android
    alias(libs.plugins.jetbrains.kotlin.android) apply false // Plugin de Kotlin
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin) apply false // Plugin para manejar secretos de Google Maps
    alias(libs.plugins.google.gms.google.services) apply false // Plugin para Google Services
}
