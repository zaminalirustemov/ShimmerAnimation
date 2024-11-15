plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
//    `maven-publish`
}

android {
    namespace = "az.lahza.shimmer_animation"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // AndroidX libraries
    implementation(libs.androidx.core.ktx) // Android KTX for Kotlin extensions
    implementation(libs.androidx.appcompat) // AppCompat for backward compatibility
    implementation(libs.material) // Material Design components

    // Testing dependencies
    testImplementation(libs.junit) // JUnit for unit tests
    androidTestImplementation(libs.androidx.junit) // JUnit for Android tests
    androidTestImplementation(libs.androidx.espresso.core) // Espresso for UI tests
}

//
//afterEvaluate{
//    publishing {
//        publications {
//            create<MavenPublication>("maven") {
//                groupId = "com.github.zaminalirustemov"
//                artifactId = "shimmer_animation"
//                version = "1.0.3"
//                from(components["release"])
//            }
//        }
//
//        repositories {
//            maven {
//                name = "GitHubPackages"
//                url = uri("https://github.com/zaminalirustemov/Shimmer_Effect")
//            }
//        }
//    }
//}