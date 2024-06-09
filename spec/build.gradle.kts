plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)

    idea
}

android {
    namespace = "com.github.hemoptysisheart.ble.spec"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

kotlin {
    sourceSets {
        named("main") {
            kotlin.srcDir("src/main/kotlin")
            kotlin.srcDir("build/generated/ksp/main/kotlin")
        }

        named("test") {
            kotlin.srcDir("build/generated/ksp/main/kotlin")
            kotlin.srcDir("src/test/kotlin")
            kotlin.srcDir("build/generated/ksp/test/kotlin")
        }
    }
}

ksp {
    arg("public.path", file("${rootProject.rootDir}/ble-spec").toString())
}

idea {
    module {
        sourceDirs = sourceDirs +
                file("build/generated/ksp/main/kotlin") +
                file("build/generated/ksp/test/kotlin")
    }
}

dependencies {
    implementation(project(":spec-annotation"))
    implementation(project(":spec-processor"))

    ksp(project(":spec-processor"))

    testImplementation(libs.kotlin.logging)
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.logback.classic)
    testImplementation(libs.mockk)
}
