plugins {
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    withSourcesJar()
}

dependencies {
    implementation(project(":spec-annotation"))

    implementation(libs.ksp.symbol.processing.api)
}
