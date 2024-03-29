import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "dev.baharudin.tmdb_android.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            if (!System.getenv("GH_ACTIONS_FLAG").toBoolean()) {
                val accessToken: String =
                    gradleLocalProperties(rootDir).getProperty("DEV_ACCESS_TOKEN")
                buildConfigField("String", "API_KEY", "\"$accessToken\"")
                buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/\"")
            }
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            val accessToken: String = if (System.getenv("GH_ACTIONS_FLAG").toBoolean()) {
                System.getenv("GH_ACTIONS_PROD_ACCESS_TOKEN")
            } else {
                gradleLocalProperties(rootDir).getProperty("PROD_ACCESS_TOKEN")
            }

            buildConfigField("String", "API_KEY", "\"$accessToken\"")
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/\"")
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
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER
    }
}

dependencies {
    // Function
    applyBasicFunctionDependencies()
    applyPagingDependencies()
    applyNetworkDependencies()
    applyLocalDbDependencies()
    applyHiltDependencies()

    // UI
    applyComposeUiDependencies()
}

ksp {
    arg(RoomSchemaArgProvider(File(projectDir, "schemas")))
}

class RoomSchemaArgProvider(
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    val schemaDir: File
) : CommandLineArgumentProvider {

    override fun asArguments(): Iterable<String> {
        return listOf("room.schemaLocation=${schemaDir.path}")
    }
}