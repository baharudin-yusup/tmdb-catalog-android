plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "dev.baharudin.themoviedb"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.baharudin.themoviedb"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            if (System.getenv("GH_ACTIONS_FLAG").toBoolean()) {
                storeFile = file("GH_ACTIONS_SIGNING_STORE_FILE_PATH")
                storePassword = System.getenv("GH_ACTIONS_SIGNING_STORE_PASSWORD")
                keyAlias = System.getenv("GH_ACTIONS_SIGNING_KEY_ALIAS")
                keyPassword = System.getenv("GH_ACTIONS_SIGNING_KEY_PASSWORD")
            }
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
    dynamicFeatures += setOf(":favorite")

//    applicationVariants.all {
//        val versionName = this.mergedFlavor.versionName
//        tasks.register("printVersionName") {
//            doLast {
//                println(versionName)
//            }
//        }
//    }
}

dependencies {
    implementation(project(":core"))

    applyBasicFunctionDependencies()
    applyBasicUiDependencies()
    applyNetworkDependencies()
    applyCoroutinesDependencies()
    applyLifecycleDependencies()
    applyHiltDependencies()
    applyPagingDependencies()
    applyRoomDependencies()
    applyGlideDependencies()
}