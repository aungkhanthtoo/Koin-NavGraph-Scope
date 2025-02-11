package com.example.koinnavscope.featureB.ui.screenB1

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal object ScreenB1

internal fun NavGraphBuilder.screenB1(
    onNavigateBack: () -> Unit,
    onNavigateToScreenC1: () -> Unit
) {
    composable<ScreenB1>{
        ScreenB1(onNavigateBack, onNavigateToScreenC1)
    }
}