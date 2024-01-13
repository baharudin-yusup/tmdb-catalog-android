import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.applyBasicFunctionDependencies() {
    implementation("androidx.core:core-ktx:1.12.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation(Dependencies.JUNIT_JUPITER)
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

fun DependencyHandler.applyAppSpecificDependencies() {
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")
}

fun DependencyHandler.applyNetworkDependencies() {
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER_GSON)
    implementation(Dependencies.OKHTTP)
    implementation(Dependencies.OKHTTP_LOGGING_INTERCEPTOR)
}

fun DependencyHandler.applyCoroutinesDependencies() {
    implementation(Dependencies.COROUTINES_CORE)
    implementation(Dependencies.COROUTINES_ANDROID)
}

fun DependencyHandler.applyLifecycleDependencies() {
    implementation(Dependencies.LIFECYCLE_VIEWMODEL_COMPOSE)
}

fun DependencyHandler.applyHiltDependencies() {
    implementation(Dependencies.HILT_ANDROID)
    implementation(Dependencies.HILT_NAVIGATION_COMPOSE)
    ksp(Dependencies.HILT_COMPILER)
}

fun DependencyHandler.applyPagingDependencies() {
    implementation(Dependencies.PAGING_RUNTIME)
    implementation(Dependencies.PAGING_COMPOSE)
}

fun DependencyHandler.applyLocalDbDependencies() {
    implementation(Dependencies.ROOM)
    implementation(Dependencies.ROOM_RUNTIME)
    implementation(Dependencies.ROOM_PAGING)
    annotationProcessor(Dependencies.ROOM_COMPILER)
    testImplementation(Dependencies.ROOM_TESTING)
    ksp(Dependencies.ROOM_COMPILER)

    // Encryptor
    implementation(Dependencies.ANDROID_DATABASE_SQLCIPHER)
    implementation(Dependencies.SQLITE)
}

fun DependencyHandler.applyFirebaseDependencies() {
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}