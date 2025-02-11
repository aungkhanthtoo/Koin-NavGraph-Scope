package com.example.koinnavscope.featureB.ui.screenB2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import com.example.koinnavscope.data.app.AppRepository
import com.example.koinnavscope.featureB.data.FeatureBRepository
import kotlinx.coroutines.flow.StateFlow

internal class ScreenB2ViewModel(
    private val repository: FeatureBRepository,
    appRepository: AppRepository
) : ViewModel() {

    val uiState: StateFlow<Int> = repository.counter

    val appState by mutableIntStateOf(appRepository.counter)

    fun increaseCounter() {
        repository.increaseCounter()
    }
}