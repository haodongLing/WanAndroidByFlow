package io.github.haodongling.dependencies

object Versions {
    const val compileSdk = 29
    const val buildTools = "29.0.3"
    const val minSdk = 21
    const val targetSdk = 29
    const val versionCode = 1
    const val versionName = "1.0"

    const val kotlin = "1.4.10"
    const val coroutines = "1.3.9"
    const val coroutines_android = "1.3.9"
    const val androidxArch = "2.0.0"
    const val mockito = "2.23.0"

    const val appcompat = "1.1.0"
    const val constraintLayout = "1.1.3"
    const val retrofit = "2.7.1"
    const val retrofit_converter_gson = "2.6.2"
    const val okhttp_logging_interceptor = "4.0.0"
    const val swipeRefreshLayout = "1.1.0-rc01"
    const val material = "1.2.0-beta01"
    const val circleImageview = "2.2.0"
    const val leakcanary = "2.0-alpha-3"
    const val baseRecyclerViewAdapterHelper = "3.0.4"
    const val glide = "4.11.0"
    const val glide_compiler = "4.11.0"
    const val cardView = "1.0.0"
    const val verticalTabLayout = "1.2.5"
    const val flowLayout = "1.1.2"
    const val persistentCookieJar = "v1.0.1"
    const val licensesDialog = "2.1.0"
    const val material_dialogs = "3.3.0"
    const val livedata_ktx = "2.2.0"
    const val viewPager2 = "1.0.0"
    const val koin = "2.0.1"
    const val core_ktx = "1.3.0"
    const val navigation = "2.2.2"
    const val recyclerView = "1.1.0"
    const val viewmodel_ktx = "2.2.0"
    const val lifecycle_extension = "2.2.0"
    const val lifecycleVersion = "2.3.1"
    const val smartrefreshlayout = "1.1.3"
    const val youth_banner = "1.4.9"
    const val permissionx = "1.4.0"
    const val unpeek_livedata = "6.0.0-beta1"
    const val navigation_fragment = "2.3.5"
    const val dokit = "3.4.0-alpha04"
    const val preference = "1.1.1"
    const val arouter = "1.5.1"
    const val statusBarUtil = "1.5.1"
    const val autoSize = "v1.2.1"
    const val TencentTBSX5 = "43939"                    // 腾讯X5WebView
    const val lottie = "2.7.0"
    const val revealLayout = "1.3.4"
    const val roundedimageview = "2.3.0"
    const val heartView = "1.0.0"
    const val blurred = "1.3.0"
    const val ponyo = "1.0.0"
    const val StatusBarCompat="2.3.3"
    const val fastJson="1.2.59";
}

object Deps {


    object AndroidX {
        // androidx
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
        const val material = "com.google.android.material:material:${Versions.material}"
        const val viewPager2 = "androidx.viewpager2:viewpager2:${Versions.viewPager2}"

        const val navigation_fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
        const val lifecycle_extension = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle_extension}"
        const val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"
        const val lifecycle_common_java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleVersion}"
        const val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
        const val lifecycle_livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
        const val koin_android = "org.koin:koin-android:${Versions.koin}"
        const val koin_androidx_scope = "org.koin:koin-androidx-scope:${Versions.koin}"
        const val koin_androidx_viewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
        const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
        const val preferences = "androidx.preference:preference:${Versions.preference}"


    }

    object Kotlin {
        // kotlin
        const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_android}"
        const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    }

    object GitHub {
        // network
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_converter_gson}"
        const val okhttp_logging_interceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_logging_interceptor}"
        const val persistentCookieJar = "com.github.franmontiel:PersistentCookieJar:${Versions.persistentCookieJar}"

        // third
        const val circleimageview = "de.hdodenhof:circleimageview:${Versions.circleImageview}"
        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"
        const val baseRecyclerViewAdapterHelper =
            "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.baseRecyclerViewAdapterHelper}"
        const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide_compiler}"

        //    const val verticalTabLayout = "q.rorbin:VerticalTabLayout:${Versions.verticalTabLayout}"
        const val flowLayout = "com.hyman:flowlayout-lib:${Versions.flowLayout}"
        const val licensesDialog = "de.psdev.licensesdialog:licensesdialog:${Versions.licensesDialog}"
        const val material_dialogs_core = "com.afollestad.material-dialogs:core:${Versions.material_dialogs}"
        const val material_dialogs_input = "com.afollestad.material-dialogs:input:${Versions.material_dialogs}"

        const val retrofit2_rxjava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
        const val smartrefreshlayout = "com.scwang.smartrefresh:SmartRefreshLayout:${Versions.smartrefreshlayout}"
        const val youth_banner = "com.youth.banner:banner:${Versions.youth_banner}"
        const val permissionx = "com.permissionx.guolindev:permissionx:${Versions.permissionx}"
        //    const val navigation = "androidx.navigation:navigation-fragment:${Versions.navigation_fragment}"
//    const val navigation_ui="androidx.navigation:navigation-ui:${Versions.navigation}"

        const val arouter_api = "com.alibaba:arouter-api:${Versions.arouter}"
        const val arouter_compiler = "com.alibaba:arouter-compiler:${Versions.arouter}"
        const val statusBarUtil = "com.jaeger.statusbarutil:library:${Versions.statusBarUtil}"
        const val autoSize = "com.github.JessYanCoding:AndroidAutoSize:${Versions.autoSize}"
        const val unpeek_livedata = "com.kunminx.arch:unpeek-livedata:${Versions.unpeek_livedata}"
        const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
        const val revealLayout = "com.github.goweii:RevealLayout:${Versions.revealLayout}"
        const val roundedimageview = "com.makeramen:roundedimageview:${Versions.roundedimageview}"
        const val heartView = "com.github.goweii:HeartView:${Versions.heartView}"
        const val blurred = "com.github.goweii:blurred:${Versions.blurred}"
        const val ponyo = "per.goweii.ponyo:ponyo:${Versions.ponyo}"
        const val ponyoLog = "per.goweii.ponyo:ponyo-log:${Versions.ponyo}"
        const val ponyoCrash = "per.goweii.ponyo:ponyo-crash:${Versions.ponyo}"
        const val StatusBarCompat="com.github.niorgai:StatusBarCompat:${Versions.StatusBarCompat}"
        const val fastJson="com.alibaba:fastjson:${Versions.fastJson}"
    }

    object SDK {
        const val TencentTBSX5 = "com.tencent.tbs.tbssdk:sdk:${Versions.TencentTBSX5}"
    }


}