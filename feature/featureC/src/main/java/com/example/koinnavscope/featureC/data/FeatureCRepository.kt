package com.example.koinnavscope.featureC.data

import kotlinx.coroutines.flow.StateFlow

interface FeatureCRepository {

    val counter: StateFlow<Int>

    fun increaseCounter(): Int
}