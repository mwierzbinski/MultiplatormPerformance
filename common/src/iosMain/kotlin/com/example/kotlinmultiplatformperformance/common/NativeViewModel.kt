package com.example.kotlinmultiplatformperformance.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalTime
class NativeViewModel {

    private val scope = MainScope(Dispatchers.Main)
    private val threadPerformance: AndroidThreadPerformance = AndroidThreadPerformance(2000)

    fun testSingleTaskOnSingleBackgroundThread(onComplete: (String) -> Unit) {
        scope.launch {
            val result = threadPerformance.testSingleTaskOnSingleBackgroundThread()
            onComplete(result)
        }
    }

    fun testSingleTaskOnMultipleBackgroundThread(onComplete: (String) -> Unit) {
        scope.launch {
            val report = mutableListOf("Android parallel report:")

            val duration = measureTime {
                report.add(threadPerformance.testSingleTaskOnMultipleBackgroundThread())
            }

            report.add("four task on a background thread took $duration in parallel")
            onComplete(report.joinToString(separator = "\n"))
        }
    }

    fun onDestroy() {
        scope.onDestroy()
    }
}
