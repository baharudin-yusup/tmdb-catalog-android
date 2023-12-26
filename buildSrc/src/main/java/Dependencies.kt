import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    // Navigation
    const val NAVIGATION_FRAGMENT =
        "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI =
        "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"

    // Network
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val OKHTTP_LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"

    // Coroutines
    const val COROUTINES_CORE =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    const val COROUTINES_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"

    // Lifecycle
    const val LIFECYCLE_VIEWMODEL =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}"

    // Dagger Hilt
    const val DAGGER = "com.google.dagger:dagger:${Versions.DAGGER}"
    const val DAGGER_COMPILER = "com.google.dagger:dagger:${Versions.DAGGER}"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_COMPILER = "com.google.dagger:hilt-compiler:${Versions.HILT}"

    // Paging
    const val PAGING_RUNTIME = "androidx.paging:paging-runtime-ktx:${Versions.PAGING}"

    // Room
    const val ROOM = "androidx.room:room-ktx:${Versions.ROOM}"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_PAGING = "androidx.room:room-paging:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val ROOM_TESTING = "androidx.room:room-testing:${Versions.ROOM}"

    // Room Encrypt
    const val ANDROID_DATABASE_SQLCIPHER = "net.zetetic:android-database-sqlcipher:4.4.0@aar"
    const val SQLITE = "androidx.sqlite:sqlite-ktx:2.4.0"

    // Glide
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"
    const val GLIDE_KSP = "com.github.bumptech.glide:ksp:${Versions.GLIDE}"

    // Test
    const val JUNIT_JUPITER = "org.junit.jupiter:junit-jupiter:5.8.1"
}

private fun DependencyHandler.implementation(dependency: Any) {
    add("implementation", dependency)
}


private fun DependencyHandler.ksp(dependency: Any) {
    add("ksp", dependency)
}

private fun DependencyHandler.testImplementation(dependency: Any) {
    add("testImplementation", dependency)
}

private fun DependencyHandler.androidTestImplementation(dependency: Any) {
    add("androidTestImplementation", dependency)
}

private fun DependencyHandler.annotationProcessor(dependency: Any) {
    add("annotationProcessor", dependency)
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
    implementation(Dependencies.LIFECYCLE_VIEWMODEL)
    implementation(Dependencies.LIFECYCLE_RUNTIME)
    implementation(Dependencies.LIFECYCLE_LIVEDATA)
}

fun DependencyHandler.applyDaggerDependencies() {
    implementation(Dependencies.DAGGER)
    ksp(Dependencies.DAGGER_COMPILER)
}

fun DependencyHandler.applyHiltDependencies() {
    implementation(Dependencies.HILT_ANDROID)
    ksp(Dependencies.HILT_COMPILER)
}

fun DependencyHandler.applyPagingDependencies() {
    implementation(Dependencies.PAGING_RUNTIME)
}

fun DependencyHandler.applyLocalDbDependencies() {
    implementation(Dependencies.ROOM)
    implementation(Dependencies.ROOM_RUNTIME)
    implementation(Dependencies.ROOM_PAGING)
    annotationProcessor(Dependencies.ROOM_COMPILER)
    testImplementation(Dependencies.ROOM_TESTING)
    ksp(Dependencies.ROOM_COMPILER)

    implementation(Dependencies.ANDROID_DATABASE_SQLCIPHER)
    implementation(Dependencies.SQLITE)
}

fun DependencyHandler.applyGlideDependencies() {
    implementation(Dependencies.GLIDE)
    annotationProcessor(Dependencies.GLIDE_COMPILER)
    ksp(Dependencies.GLIDE_KSP)
}

fun DependencyHandler.applyBasicFunctionDependencies() {
    implementation("androidx.core:core-ktx:1.12.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation(Dependencies.JUNIT_JUPITER)
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

fun DependencyHandler.applyBasicUiDependencies() {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.google.android.flexbox:flexbox:3.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation(Dependencies.NAVIGATION_FRAGMENT)
    implementation(Dependencies.NAVIGATION_UI)
}