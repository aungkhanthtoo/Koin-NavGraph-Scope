package com.example.koinnavscope.featureA.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class FeatureARepositoryImpl : FeatureARepository {

    override val counter: StateFlow<Int>
        field = MutableStateFlow(0)

    override fun increaseCounter(): Int {
        return ++counter.value
    }
}