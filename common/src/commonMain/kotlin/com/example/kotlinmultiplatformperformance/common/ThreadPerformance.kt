package com.example.kotlinmultiplatformperformance.common

import kotlinx.coroutines.*

// package org.example.kotlin.multiplatform.coroutines
// import kotlinx.coroutines.CoroutineScope

class ThreadPerformance {
    private val testArray = (0..5000).toList()

    fun onMainThread() {
        compute()
    }

    fun singleTask(): List<Int> {
        return bubbleSort(testArray)
    }

    fun doSomeWork(callback: (() -> Unit)) {

        GlobalScope.launch {
            // heavy operation here that returns a Result
            compute()
            withContext(Dispatchers.Main) {
                callback()
            }
        }
    }
    fun singleTaskOnMultipleThreads() {
        // For running compute on multiple background threads
        // Not sure if that is working correctly atm. We hope to spawn multiple threads and work on the task in parallel
        for (currentIndex in 0 until 5) {
            GlobalScope.launch {
                // heavy operation here that returns a Result
                compute()
            }
        }
    }

    fun multipleTaskOnSingleBackgroundThread() {
        // For running compute multiple times on background thread
        // Not sure if that is working correctly atm. We would like to start multi[le tasks on a single background thread
        GlobalScope.launch {
            for (currentIndex in 0 until 5) {
                 compute()
            }
        }
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