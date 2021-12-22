plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

val javaVersion = JavaVersion.VERSION_11
val androidVersion = 31
val androidBuildTools = "30.0.3"
val kotlinCompilerExtensionVersion = "1.0.5"

android {
    compileSdk = androidVersion
    defaultConfig {
        applicationId = "com.gmail.yaroslavlancelot.open_technarium"
        minSdk = 21
        versionName = "2.0.0"
        versionCode = 5
        targetSdk = androidVersion
        buildToolsVersion = androidBuildTools
        resourceConfigurations.addAll(listOf("en", "uk", "ru"))

        javaCompileOptions {
            annotationProcessorOptions {
                arguments.putAll(
                    listOf(
                        Pair("room.schemaLocation", "$projectDir/schemas"),
                        Pair("room.incremental", "true"),
                        Pair("room.expandProjection", "true")
                    )
                )
            }
        }
    }
    signingConfigs {
        create("release") {
            val map: Map<String, String>
            val propertiesFile = File(projectDir.path + "/signing/passwords.properties")
            if (propertiesFile.exists()) {
                map = propertiesFile.readLines().map {
                    val indexOf = it.indexOf("=")
                    Pair(it.substring(0, indexOf), it.substring(indexOf + 1))
                }.toMap()
                keyAlias = map["alias"]
                keyPassword = map["key_password"]
                storePassword = map["store_password"]
                storeFile = File("signing/technarium_keystore.der")
            }
        }
    }

    buildTypes {
        getByName("release") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        targetCompatibility = javaVersion
        sourceCompatibility = javaVersion
    }

    sourceSets["main"].java {
        srcDir("src/main/kotlin/")
    }

    sourceSets["test"].java {
        srcDir("src/test/kotlin/")
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = this@Build_gradle.kotlinCompilerExtensionVersion
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    //Dependency Injection
    val hiltVersion = "2.38.1"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0-beta01")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    //Appcompat
    val appcompatVersion = "1.3.1"
    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("androidx.appcompat:appcompat-resources:$appcompatVersion")
    //Material Design
    implementation("com.google.android.material:material:1.4.0")
    //Compose
    val composeVersion = kotlinCompilerExtensionVersion
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-ripple:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion") //remove if you do not use LiveData
    //Navigation
    implementation("androidx.navigation:navigation-compose:2.4.0-beta02")
    //ktx
    implementation("androidx.activity:activity-ktx:1.4.0")


    /* Testing */
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test:runner:1.4.0")
}

kapt {
    correctErrorTypes = true //Hilt: Allow references to generated code
}
