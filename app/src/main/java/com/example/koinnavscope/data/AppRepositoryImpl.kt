package com.example.koinnavscope.data

import com.example.koinnavscope.data.app.AppRepository
import java.util.concurrent.atomic.AtomicInteger

internal class AppRepositoryImpl : AppRepository {

    private val _counter = AtomicInteger(0)
    override val counter: Int
        get() = _counter.get()

    override fun increaseCounter(): Int {
        return _counter.incrementAndGet()
    }
}