package com.example.kotlinmultiplatformperformance.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class Greeting {
    val mainScope: CoroutineScope = CustomMainScope()

    @ExperimentalTime
    fun greeting(callback: (String) -> Unit) {
        mainScope.launch {
            val androidThreadPerformance = AndroidThreadPerformance(2000, this)

            mutableListOf(androidThreadPerformance.testSingleTaskOnSingleBackgroundThread {
                callback(it)
            })

            val duration = measureTime {
                androidThreadPerformance.testSingleTaskOnMultipleBackgroundThread {
                    callback(it)
                }
            }

            callback("four task on a background thread took $duration in parallel")
        }
    }
}
