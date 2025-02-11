package com.example.koinnavscope.featureA.data

import kotlinx.coroutines.flow.StateFlow

interface FeatureARepository {

    val counter: StateFlow<Int>

    fun increaseCounter(): Int
}