package com.example.kotlinmultiplatformperformance.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalTime
class AndroidThreadPerformance(private val size: Int, private val scope: CoroutineScope) {

    fun testSingleTaskOnSingleBackgroundThread(callback: (String) -> Unit) {
        scope.launch {
            val measureTime = measureTime {
                val single = compute()
            }
            callback("single task on a background thread took $measureTime")
        }
    }

    suspend fun testSingleTaskOnMultipleBackgroundThread(callback: (String) -> Unit) {
        scope.launch {

            val async1 = async { compute() }
            val async2 = async { compute() }
            val async3 = async { compute() }
            val async4 = async { compute() }
            val async5 = async { compute() }

            val total =
                async1.await() + async2.await() + async3.await() + async4.await() + async5.await()
            callback("four task on a background thread took $total adding them up")
        }
    }

    private fun compute(): Duration = measureTime {
        val testArray =  (0..size).toList()

        bubbleSort(testArray)
    }

    private fun bubbleSort(array: List<Int>): List<Int> {
        val sorted = array.toMutableList()
        var didSwitch = true
        while (didSwitch) {
            didSwitch = false
            for (currentIndex in 1 until array.size) {
                val shouldSwitch = sorted[currentIndex] > sorted[currentIndex - 1]
                if (shouldSwitch) {
                    val temp = sorted[currentIndex - 1]
                    sorted[currentIndex - 1] = sorted[currentIndex]
                    sorted[currentIndex] = temp
                    didSwitch = true
                }
            }
        }
        return sorted
    }
}
