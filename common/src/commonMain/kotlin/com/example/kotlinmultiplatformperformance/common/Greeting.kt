package com.example.kotlinmultiplatformperformance.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime


class Greeting {

    @ExperimentalTime
    fun greeting(callback: (String) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            val androidThreadPerformance = AndroidThreadPerformance(2000)

            val report = mutableListOf(androidThreadPerformance.testSingleTaskOnSingleBackgroundThread())

            val duration = measureTime {
                report.add(androidThreadPerformance.testSingleTaskOnMultipleBackgroundThread())
            }

            report.add("four task on a background thread took $duration in parallel")

            callback(report.joinToString(separator = "\n"))
        }
    }
}
