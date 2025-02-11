package com.example.koinnavscope.featureC.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class FeatureCRepositoryImpl : FeatureCRepository {

    override val counter: StateFlow<Int>
        field = MutableStateFlow(0)

    override fun increaseCounter(): Int {
        return ++counter.value
    }
}