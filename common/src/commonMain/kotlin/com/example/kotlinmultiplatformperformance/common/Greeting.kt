package com.example.kotlinmultiplatformperformance.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class Greeting {
    @ExperimentalTime
    fun greeting(callback: (String) -> Unit) {
        callback("Currently not implemented :D")
    }
}
