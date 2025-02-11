package com.example.koinnavscope.featureC.ui.screenC2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import com.example.koinnavscope.data.app.AppRepository
import com.example.koinnavscope.featureC.data.FeatureCRepository
import kotlinx.coroutines.flow.StateFlow

internal class ScreenC2ViewModel(
    private val repository: FeatureCRepository,
    appRepository: AppRepository
) : ViewModel() {

    val uiState: StateFlow<Int> = repository.counter

    val appState by mutableIntStateOf(appRepository.counter)

    fun increaseCounter() {
        repository.increaseCounter()
    }
}