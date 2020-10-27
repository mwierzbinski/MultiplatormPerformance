package com.example.kotlinmultiplatformperformance.common

import kotlinx.coroutines.CoroutineDispatcher

expect class Platform() {
    internal val Main: CoroutineDispatcher
    internal val Background: CoroutineDispatcher
    val platform: String
}