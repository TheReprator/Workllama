object AndroidSdk {
    const val min = 21
    const val compile = 30
    const val target = compile

    val locales = listOf("en", "hi")
}

object AppConstant {
    const val applicationPackage = "io.bloco.template"
    const val name = "Workllama"
    const val host = "https://iie-service-dev.workingllama.com/iie-service/"
    const val hostConstant = "HOST"
    const val flavourDimension = "type"
}

object AppVersion {
    const val versionCode = 1
    const val versionName = "1.0"
}

object AppModules {
    const val moduleApp = ":app"
    const val moduleBaseAndroid = ":base-android"
    const val moduleBaseJava = ":base"
    const val moduleNavigation = ":navigation"
    const val moduleDatabase = ":database"

    const val moduleContactSharedModule = ":appModules:contactSharedModule"
    const val moduleContactList = ":appModules:contactList"
    const val moduleContactDetail = ":appModules:contactDetail"
}

object Libs {

    object Versions{
        const val kotlin = "1.5.20"
        const val spotless = "5.14.0"
        const val dokka = "1.4.32"
        const val ktlint = "0.40.0"
        const val detekt = "1.17.1"
    }

    object Plugins {
        const val androidApplication = "com.android.application"
        const val crashlytics = "com.google.firebase.crashlytics"
        const val androidLibrary = "com.android.library"
        const val javaLibrary = "java-library"
        const val kotlinLibrary = "kotlin"
        const val kotlinJVM = "jvm"
        const val kotlinAndroid = "android"
        const val kotlinKapt = "kapt"
        const val kaptDagger = "dagger.hilt.android.plugin"
        const val kotlinNavigation = "androidx.navigation.safeargs.kotlin"
        const val dokka = "org.jetbrains.dokka"
        const val spotless = "com.diffplug.spotless"
        const val detekt = "io.gitlab.arturbosch.detekt"
    }

    const val inject = "javax.inject:javax.inject:1"
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.2.2"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val facebook = "com.facebook.android:facebook-login:6.1.0"
    const val countryPicker = "com.hbb20:ccp:2.3.8"
    const val crashlytics = "com.google.firebase:firebase-crashlytics-gradle:2.1.1"
    const val swiperefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    object Coil {
        private const val version = "1.1.1"
        const val coil = "io.coil-kt:coil:$version"
    }

    object Google {
        const val materialWidget = "com.google.android.material:material:1.3.0"
        const val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1"
        const val gmsGoogleServices = "com.google.gms:google-services:4.3.4"
    }

    object Firebase {
        const val firebaseAnalytics = "com.google.firebase:firebase-analytics:17.4.3"
        const val core = "com.google.firebase:firebase-core:18.0.1"
        const val iid = "com.google.firebase:firebase-iid:21.0.1"
        const val messaging = "com.google.firebase:firebase-messaging:21.0.1"
        const val authFirebase = "com.google.firebase:firebase-auth:20.0.1"
        const val authPlayServices = "com.google.android.gms:play-services-auth:19.0.0"
        const val authPlayPhoneServices =
            "com.google.android.gms:play-services-auth-api-phone:17.5.0"
    }

    object Kotlin {
        private const val kotlinVersion = "1.5.20"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$kotlinVersion"
    }

    object Coroutines {
        private const val version = "1.4.3"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.2.0"
        const val cardView = "androidx.cardview:cardview:1.0.0"
        const val annotation = "androidx.annotation:annotation:1.2.0"
        const val multidex = "androidx.multidex:multidex:2.0.1"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val coreKtx = "androidx.core:core-ktx:1.3.2"
        const val viewPager2 = "androidx.viewpager2:viewpager2:1..0.0"

        const val browser = "androidx.browser:browser:1.3.0"

        object Fragment {
            private const val version = "1.3.5"
            const val fragment = "androidx.fragment:fragment:$version"
            const val fragmentKtx = "androidx.fragment:fragment-ktx:$version"
        }

        object Lifecycle {
            private const val version = "2.3.1"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Navigation {
            private const val version = "2.3.5"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
            const val navigationPlugin =
                "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
            const val test = "androidx.navigation:navigation-testing:$version"
        }

        object Room {
            private const val version = "2.3.0"
            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val common = "androidx.room:room-common:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val test = "androidx.room:room-testing:$version"
        }

        object MapV3 {
            const val map = "com.google.android.gms:play-services-maps:17.0.0"
            const val baseMent = "com.google.android.gms:play-services-basement:17.5.0"
            const val base = "com.google.android.gms:play-services-base:17.5.0"
            const val gcm = "com.google.android.gms:play-services-gcm:17.0.0"
            const val location = "com.google.android.gms:play-services-location:17.1.0"
        }

        object Work {
            private const val version = "2.5.0-rc01"
            const val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
            const val test = "androidx.work:work-testing:$version"
        }
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val jacksonConverter = "com.squareup.retrofit2:converter-jackson:$version"
        const val jacksonKotlinModule = "com.fasterxml.jackson.module:jackson-module-kotlin:2.12.2"
    }

    object OkHttp {
        private const val version = "4.9.1"
        const val okhttp = "com.squareup.okhttp3:okhttp:$version"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:$version"
    }

    object Dagger {
        private const val version = "2.38"
        const val runtime = "com.google.dagger:dagger:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
        const val javaxInject = "javax.inject:javax.inject:1"
    }

    object DaggerHilt {
        private const val version = "2.38"

        const val classPath = "com.google.dagger:hilt-android-gradle-plugin:$version"

        const val hilt = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-android-compiler:$version"
    }

    object TestDependencies {
        const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}