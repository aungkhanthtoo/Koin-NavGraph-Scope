package com.example.koinnavscope.featureA.ui.screenA1

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import com.example.koinnavscope.data.app.AppRepository
import com.example.koinnavscope.featureA.data.FeatureARepository
import kotlinx.coroutines.flow.StateFlow

internal class ScreenA1ViewModel(
    private val repository: FeatureARepository,
    appRepository: AppRepository
) : ViewModel() {

    val uiState: StateFlow<Int> = repository.counter

    val appState by mutableIntStateOf(appRepository.counter)

    fun increaseCounter() {
        repository.increaseCounter()
    }
}