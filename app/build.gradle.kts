import java.io.FileInputStream
import java.util.Locale
import java.util.Properties

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
        versionCode = 2
        versionName = "1.1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            if (System.getenv("GH_ACTIONS_FLAG").toBoolean()) {
                storeFile = file(System.getenv("GH_ACTIONS_SIGNING_STORE_FILE_PATH"))
                storePassword = System.getenv("GH_ACTIONS_SIGNING_STORE_PASSWORD")
                keyAlias = System.getenv("GH_ACTIONS_SIGNING_KEY_ALIAS")
                keyPassword = System.getenv("GH_ACTIONS_SIGNING_KEY_PASSWORD")
            } else {
                // Create a variable called keystorePropertiesFile, and initialize it to your
                // keystore.properties file, in the rootProject folder.
                val keystorePropertiesFile = rootProject.file("keystore.properties")

                // Initialize a new Properties() object called keystoreProperties.
                val keystoreProperties = Properties()

                // Load your keystore.properties file into the keystoreProperties object.
                keystoreProperties.load(FileInputStream(keystorePropertiesFile))

                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("release")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    dynamicFeatures += setOf(":favorite")

    applicationVariants.all {
        val variant = this
        val variantName = variant.name
        val variantVersionName = variant.versionName ?: "Not found"

        tasks.register(
            "printVersionName${
                variantName.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }"
        ) {
            group = "Custom tasks"
            description = "Prints versionName for $variantName build type"
            doLast {
                println(variantVersionName)
            }
        }
    }
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
    applyLocalDbDependencies()
    applyGlideDependencies()

    // Specific dependencies
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")

    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    // Material Design 3
    implementation("androidx.compose.material3:material3")
    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // Optional - Integration with activities
    implementation("androidx.activity:activity-compose:1.8.2")
    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    // Optional - Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")
}
