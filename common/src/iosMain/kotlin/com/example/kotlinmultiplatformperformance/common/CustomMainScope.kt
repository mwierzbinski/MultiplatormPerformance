package com.example.kotlinmultiplatformperformance.common

import kotlinx.coroutines.*
import platform.darwin.*
import kotlin.coroutines.CoroutineContext

@UseExperimental(InternalCoroutinesApi::class)
private class MainDispatcher : CoroutineDispatcher(), Delay {
    @Suppress("TooGenericExceptionCaught")
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue()) {
            try {
                block.run()
            } catch (err: Throwable) {
                logError("UNCAUGHT", err.message ?: "", err)
                throw err
            }
        }
    }

    @InternalCoroutinesApi
    @Suppress("TooGenericExceptionCaught")
    override fun scheduleResumeAfterDelay(
        timeMillis: Long,
        continuation: CancellableContinuation<Unit>
    ) {
        dispatch_after(
            dispatch_time(DISPATCH_TIME_NOW, timeMillis * 1_000_000),
            dispatch_get_main_queue()
        ) {
            try {
                with(continuation) {
                    resumeUndispatched(Unit)
                }
            } catch (err: Throwable) {
                logError("UNCAUGHT", err.message ?: "", err)
                throw err
            }
        }
    }

    @InternalCoroutinesApi
    @Suppress("TooGenericExceptionCaught")
    override fun invokeOnTimeout(
        timeMillis: Long,
        block: Runnable,
        context: CoroutineContext
    ): DisposableHandle {
        val handle = object : DisposableHandle {
            var disposed = false
                private set

            override fun dispose() {
                disposed = true
            }
        }
        dispatch_after(
            dispatch_time(DISPATCH_TIME_NOW, timeMillis * 1_000_000),
            dispatch_get_main_queue()
        ) {
            try {
                if (!handle.disposed) {
                    block.run()
                }
            } catch (err: Throwable) {
                logError("UNCAUGHT", err.message ?: "", err)
                throw err
            }
        }

        return handle
    }
}

private fun logError(label: String, message: String, throwable: Throwable) {
    println("$label: $message")
    throwable.printStackTrace()
}

public actual fun CustomMainScope(): CoroutineScope = CustomMainScopeImpl()

internal class CustomMainScopeImpl : CoroutineScope {
    private val dispatcher = MainDispatcher()
    private val job = Job()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("${throwable.message}: ${throwable.cause}")
    }

    override val coroutineContext: CoroutineContext
        get() = dispatcher + job + exceptionHandler
}
