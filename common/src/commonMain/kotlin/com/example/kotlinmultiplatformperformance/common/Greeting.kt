package com.example.kotlinmultiplatformperformance.common


class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}