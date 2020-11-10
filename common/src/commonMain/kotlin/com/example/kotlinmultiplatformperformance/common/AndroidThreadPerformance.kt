package com.example.kotlinmultiplatformperformance.common

import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalTime
class AndroidThreadPerformance(private val size: Int, private val dispatcherProvider: DispatcherProvider) {
    suspend fun testSingleTaskOnSingleBackgroundThread() = withContext(dispatcherProvider.background) {
        val measureTime = measureTime {
            val single = compute()
        }
        "single task on a background thread took $measureTime"
    }

    suspend fun testSingleTaskOnMultipleBackgroundThread() = withContext(dispatcherProvider.background) {
        val async1 = async { compute() }
        val async2 = async { compute() }
        val async3 = async { compute() }
        val async4 = async { compute() }
        val async5 = async { compute() }

        val total = async1.await() + async2.await() + async3.await() + async4.await() + async5.await()
        "four task on a background thread took $total adding them up"
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

    suspend fun testLaunchMultithreading() = withContext(dispatcherProvider.background) {
        //  This manages to execute tasks on multiple threads. Kinda working version

        launch(dispatcherProvider.background) {
            val duration = compute()
            println("Default $duration             : I'm working in thread Def")
        }
        launch(dispatcherProvider.background) {
            val duration = compute()
            println("newSingleThreadContext: $duration  I'm working in thread 1")
        }
        launch(dispatcherProvider.background) {
            val duration = compute()
            println("newSingleThreadContext: $duration I'm working in thread 2 ")
        }

        val dispatcher3 = dispatcherProvider.background
        launch(dispatcher3) {
            val duration = compute()
            println("newSingleThreadContext: $duration I'm working in thread 3")
        }

    }

    suspend fun testAsyncMultithreading() = withContext(dispatcherProvider.background) {

        val async1 = async (dispatcherProvider.background) { compute() }

        val async2 = async (dispatcherProvider.background) { compute() }
        val async3 = async (dispatcherProvider.background) { compute() }
        val async4 = async (dispatcherProvider.background) { compute() }
        val async5 = async (dispatcherProvider.background) { compute() }

        val total = async1.await() + async2.await() + async3.await() + async4.await() + async5.await()
        println("four task on a background thread took $total adding them up")
    }
}