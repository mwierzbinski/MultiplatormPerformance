package com.example.kotlinmultiplatformperformance.common

import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalTime
class AndroidThreadPerformance(private val size: Int) {

    suspend fun testSingleTaskOnSingleBackgroundThread() = withContext(Platform().Background) {
        val measureTime = measureTime {
            val single = compute()
        }
        "single task on a background thread took $measureTime"
    }

    suspend fun testSingleTaskOnMultipleBackgroundThread() = withContext(Platform().Background) {
        val async1 = async { compute() }
        val async2 = async { compute() }
        val async3 = async { compute() }
        val async4 = async { compute() }
        val async5 = async { compute() }

        val total =
            async1.await() + async2.await() + async3.await() + async4.await() + async5.await()
        "four task on a background thread took $total adding them up"
    }

    private fun compute(): Duration = measureTime {
        val random = Random(size)
        val testArray = IntArray(size) { random.nextInt() }

        bubbleSort(testArray.toList())
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
