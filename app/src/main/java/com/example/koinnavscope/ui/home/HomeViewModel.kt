package com.example.koinnavscope.ui.home

import androidx.lifecycle.ViewModel
import com.example.koinnavscope.data.app.AppRepository
import com.example.koinnavscope.featureA.di.featureAKoinModule
import com.example.koinnavscope.featureA.di.featureAKoinScopeModule
import com.example.koinnavscope.featureB.di.featureBKoinModule
import com.example.koinnavscope.featureB.di.featureBKoinScopeModule
import com.example.koinnavscope.featureC.di.featureCKoinModule
import com.example.koinnavscope.featureC.di.featureCKoinScopeModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.Koin
import org.koin.core.context.GlobalContext

class HomeViewModel(
    private val repository: AppRepository,
) : ViewModel() {

    private val koin: Koin = GlobalContext.get()

    val uiState: StateFlow<HomeUiState>
        field = MutableStateFlow(HomeUiState(repository.counter))

    fun increaseCounter() {
        uiState.update {
            it.copy(counter = repository.increaseCounter())
        }
    }

    fun setDiScope(di: KoinScope) {
        uiState.update {
            when (di) {
                KoinScope.RootScope -> {
                    koin.unloadModules(koinNavModules)
                    koin.loadModules(koinRootModules)
                }

                KoinScope.NavScope -> {
                    koin.unloadModules(koinRootModules)
                    koin.loadModules(koinNavModules)
                }
            }
            it.copy(di = di)
        }
    }
}

data class HomeUiState(
    val counter: Int,
    val di: KoinScope = KoinScope.NavScope
)

enum class KoinScope {
    NavScope,
    RootScope
}

private val koinNavModules = listOf(
    featureAKoinScopeModule,
    featureBKoinScopeModule,
    featureCKoinScopeModule
)
private val koinRootModules = listOf(
    featureAKoinModule,
    featureBKoinModule,
    featureCKoinModule
)