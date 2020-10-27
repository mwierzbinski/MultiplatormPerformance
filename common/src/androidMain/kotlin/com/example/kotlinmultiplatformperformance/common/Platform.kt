package com.example.kotlinmultiplatformperformance.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class Platform actual constructor() {
    internal actual val Main: CoroutineDispatcher = Dispatchers.Main
    internal actual val Background: CoroutineDispatcher = Dispatchers.Default

    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}