package com.example.kotlinmultiplatformperformance.common
import kotlinx.coroutines.*


class AndroidThreadPerformance(size: Int) {
    private val testArray = (0..size).toList();

    fun testSingleTaskOnSingleBackgroundThread() {
        GlobalScope.launch {
            // heavy operation here that returns a Result
            compute()
//            withContext(Dispatchers.Main) {
//                callback()
//            }
        }
    }

    fun testMultipleTaskOnMultipleBackgroundThread() {
        // For running compute on multiple background threads
        // Not sure if that is working correctly atm. We hope to spawn multiple threads and work on the tasks in parallel
        for (currentIndex in 0 until 5) {
            GlobalScope.launch {
                // heavy operation here that returns a Result
                compute()
            }
        }
    }

    fun testSequentialTasksOnSingleBackgrounThread() {
        // Not sure if we need that?
    }

    fun singleTask(): List<Int> {
        return bubbleSort(testArray)
    }

    private fun compute() {
        bubbleSort(testArray)
    }

    private fun bubbleSort(array: List<Int>): List<Int> {
        var sorted = array.toMutableList()
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