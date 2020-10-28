package com.example.kotlinmultiplatformperformance.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

actual fun CustomMainScope(): CoroutineScope = MainScope()
