package com.example.kotlinmultiplatformperformance.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext

actual class DispatcherProvider {
    actual val main: CoroutineDispatcher = Dispatchers.Main
    actual val background: CoroutineDispatcher
    get() =  newSingleThreadContext("BackgroundThread")
}