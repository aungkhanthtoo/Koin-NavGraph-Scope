package com.example.koinnavscope.featureB.data

import kotlinx.coroutines.flow.StateFlow

interface FeatureBRepository {

    val counter: StateFlow<Int>

    fun increaseCounter(): Int
}