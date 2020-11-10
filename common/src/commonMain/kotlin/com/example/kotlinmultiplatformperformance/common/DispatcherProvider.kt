package com.example.kotlinmultiplatformperformance.common

import kotlinx.coroutines.CoroutineDispatcher

expect class DispatcherProvider {
    val main: CoroutineDispatcher
    val background: CoroutineDispatcher

}