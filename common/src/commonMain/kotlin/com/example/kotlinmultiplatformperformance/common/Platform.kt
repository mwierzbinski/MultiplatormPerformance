package com.example.kotlinmultiplatformperformance.common

import kotlinx.coroutines.CoroutineDispatcher

expect class Platform() {
    val platform: String
}
