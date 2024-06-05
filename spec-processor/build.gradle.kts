plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    withSourcesJar()
}

dependencies {
    implementation(project(":spec-annotation"))

    implementation(libs.ksp.symbol.processing.api)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kaml)
}
