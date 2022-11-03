plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
    }
}

dependencies {
    implementation(Compose.compiler)
    implementation(Compose.ui)
    implementation(Compose.icons)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.material)
    implementation(Compose.runtime)
    implementation(Compose.navigation)
    implementation(Compose.viewModelCompose)
    implementation(Compose.lifecycleRuntime)
    implementation(Compose.lifecycleViewModelSavedState)
    implementation(Compose.activityCompose)

    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)


    implementation(project(Modules.core))
    implementation(project(Modules.corePresentation))
    implementation(project(Modules.authData))
    implementation(project(Modules.authDomain))
    implementation(project(Modules.authPresentation))
    implementation(project(Modules.controllerData))
    implementation(project(Modules.controllerDomain))
    implementation(project(Modules.navigation))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)

    implementation(Coil.coilCompose)

    implementation(Google.material)

    implementation(Retrofit.okHttp)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Retrofit.moshiConverter)

    kapt(Room.roomCompiler)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)

    Accompanist.accompanistDependencies.forEach { implementation(it) }
    Amazon.awsDependencies.forEach { implementation(it) }
    Amazon.amplifyDependencies.forEach { implementation(it) }

    testImplementation(Testing.junit4)
    testImplementation(Testing.junitAndroidExt)
    testImplementation(Testing.truth)
    testImplementation(Testing.coroutines)
    testImplementation(Testing.turbine)
    testImplementation(Testing.composeUiTest)
    testImplementation(Testing.mockk)
    testImplementation(Testing.mockWebServer)

    androidTestImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.truth)
    androidTestImplementation(Testing.coroutines)
    androidTestImplementation(Testing.turbine)
    androidTestImplementation(Testing.composeUiTest)
    androidTestImplementation(Testing.mockkAndroid)
    androidTestImplementation(Testing.mockWebServer)
    androidTestImplementation(Testing.hiltTesting)
    kaptAndroidTest(DaggerHilt.hiltCompiler)
    androidTestImplementation(Testing.testRunner)
}

/*

dependencies {
    // Amazon SDK
    // github.com/aws-amplify/aws-sdk-android
    // github.com/aws-amplify/amplify-android
    implementation "com.amplifyframework:core:$amplify_version"
    implementation "com.amplifyframework:aws-api:$amplify_version"
    implementation "com.amplifyframework:aws-auth-cognito:$amplify_version"
    implementation "com.amplifyframework:aws-datastore:$amplify_version"
    implementation "com.amazonaws:aws-android-sdk-core:$aws_version"
    implementation "com.amazonaws:aws-android-sdk-cognitoauth:$aws_version"
    implementation "com.amazonaws:aws-android-sdk-cognitoidentityprovider:$aws_version"
    implementation "com.amazonaws:aws-android-sdk-cognitoidentityprovider-asf:$aws_version"


    // Serialization
    implementation 'org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2'
    // Retrofit
    // https://github.com/square/retrofitokhttp3
    // https://github.com/square/okhttp
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:okhttp:4.9.3'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.9.3'

    // Datastore
    // https://developer.android.com/codelabs/android-preferences-datastore#4
    implementation "androidx.datastore:datastore-preferences:1.0.0"
    implementation "androidx.datastore:datastore:1.0.0"
    implementation "androidx.datastore:datastore-rxjava2:1.0.0"
    implementation "androidx.datastore:datastore-rxjava3:1.0.0"

    // ROOM
    implementation 'androidx.room:room-runtime:2.4.3'
    kapt 'androidx.room:room-compiler:2.4.3'
    implementation 'androidx.room:room-ktx:2.4.3'

    // Coil - Image Loading Library
    implementation 'io.coil-kt:coil-compose:2.0.0-rc01'

    // ICONS
    implementation "androidx.compose.material:material-icons-extended:$compose_version"

    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1'

    // Coroutine Lifecycle Scopes
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"

    // Dagger-Hilt
    implementation 'com.google.dagger:hilt-android:2.43.2'
    implementation 'com.google.android.gms:play-services-maps:18.1.0'
    kapt 'com.google.dagger:hilt-android-compiler:2.43.2'
    kapt 'androidx.hilt:hilt-compiler:1.0.0'
    implementation 'androidx.hilt:hilt-navigation-compose:1.0.0'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-savedstate:2.5.1'

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.5.2")
    implementation("com.google.android.gms:play-services:12.0.1")


    // Navigation library
    // Commented out due to a switch to use accompanist library
    // implementation 'io.github.raamcosta.compose-destinations:core:1.1.5-beta'
    // ksp 'io.github.raamcosta.compose-destinations:ksp:1.1.5-beta'

    // Maps
    implementation 'com.google.maps.android:android-maps-utils:2.3.0'
    implementation 'com.google.android.gms:play-services-location:20.0.0'

    // Live Data States (image)
    implementation "androidx.compose.runtime:runtime-livedata:$compose_version"

    // Accompanist Pager
    implementation 'com.google.accompanist:accompanist-pager:0.13.0'
    implementation 'com.google.accompanist:accompanist-pager-indicators:0.13.0'

    // Permissions
    implementation "com.google.accompanist:accompanist-permissions:0.21.1-beta"

    // FLOW
    // https://google.github.io/accompanist/flowlayout/
    implementation "com.google.accompanist:accompanist-flowlayout:0.20.3"

    // Navigation Animated Library
    // This is important to avoid the goofy awkward look of the top bar fading in and out
    implementation "com.google.accompanist:accompanist-navigation-animation:0.22.1-rc"

    implementation 'androidx.core:core-ktx:1.8.0'
    implementation 'androidx.appcompat:appcompat:1.5.0'
    implementation 'com.google.android.material:material:1.6.1'
    implementation "androidx.compose.ui:ui:$compose_version"
    implementation "androidx.compose.material:material:$compose_version"
    implementation "androidx.compose.ui:ui-tooling-preview:$compose_version"
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.5.1'
    implementation 'androidx.activity:activity-compose:1.5.1'
    testImplementation 'junit:junit:+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
    androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_version"
    debugImplementation "androidx.compose.ui:ui-tooling:$compose_version"

    // Splash Screen
    implementation "androidx.core:core-splashscreen:1.0.0"
}


 */