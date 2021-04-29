import com.android.build.gradle.internal.tasks.factory.dependsOn
import java.util.UUID

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "li.ebc.vosk4tasker"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
        ndk {
            abiFilters.add("armeabi-v7a")
            abiFilters.add("arm64-v8a")
            abiFilters.add("x86_64")
            abiFilters.add("x86")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    sourceSets.getByName("main") {
        assets.srcDir("$buildDir/generated/assets")
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")

    implementation("com.joaomgcd:taskerpluginlibrary:0.4.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-RC")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0-RC")

    implementation("net.java.dev.jna:jna:5.8.0@aar")
    implementation("com.alphacephei:vosk-android:0.3.23")
}

// VOSK assets
// https://github.com/alphacep/vosk-android-demo/blob/master/models/build.gradle

tasks.preBuild.dependsOn(tasks.register("genUUID") {
    val uuid = UUID.randomUUID()
    val odir = file("$buildDir/generated/assets/model")
    val ofile = file("$odir/uuid")

    doLast {
        mkdir(odir)
        ofile.writeText(uuid.toString())
    }
})