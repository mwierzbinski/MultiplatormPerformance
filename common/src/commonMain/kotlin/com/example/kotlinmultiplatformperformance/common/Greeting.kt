package com.example.kotlinmultiplatformperformance.common


class Greeting {
    fun greeting(): String {
        AndroidThreadPerformance(500)

        return "Hello, ${Platform().platform}!"
    }
}