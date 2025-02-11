package com.example.koinnavscope.featureB.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class FeatureBRepositoryImpl : FeatureBRepository {

    override val counter: StateFlow<Int>
        field = MutableStateFlow(0)

    override fun increaseCounter(): Int {
        return ++counter.value
    }
}