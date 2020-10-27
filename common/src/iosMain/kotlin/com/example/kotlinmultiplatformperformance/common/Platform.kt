package com.example.kotlinmultiplatformperformance.common


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import org.example.kotlin.multiplatform.coroutines.MainDispatcher
import platform.UIKit.UIDevice
import platform.darwin.dispatch_async
import platform.darwin.dispatch_queue_t
import kotlin.coroutines.CoroutineContext

actual class Platform actual constructor() {
    internal actual val Main: CoroutineDispatcher = MainDispatcher()
    internal actual val Background: CoroutineDispatcher = Main

    internal class NsQueueDispatcher(
        private val dispatchQueue: dispatch_queue_t
    ) : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            dispatch_async(dispatchQueue) {
                block.run()
            }
        }
    }

    actual val platform: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}