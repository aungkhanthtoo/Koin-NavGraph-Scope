package com.example.koinnavscope.featureB.ui.screenB1

import com.example.koinnavscope.nav.NavGraphScopeBuilder
import com.example.koinnavscope.nav.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
internal object ScreenB1

internal fun NavGraphScopeBuilder.screenB1(
    onNavigateBack: () -> Unit,
    onNavigateToScreenC1: () -> Unit
) {
    composable<ScreenB1> {
        ScreenB1(onNavigateBack, onNavigateToScreenC1, koinViewModel())
    }
}