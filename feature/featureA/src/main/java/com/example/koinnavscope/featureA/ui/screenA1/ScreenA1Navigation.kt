package com.example.koinnavscope.featureA.ui.screenA1

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal object ScreenA1

internal fun NavGraphBuilder.screenA1(
    onNavigateBack: () -> Unit,
    onNavigateToScreenA2: () -> Unit
) {
    composable<ScreenA1>{
        ScreenA1(onNavigateBack, onNavigateToScreenA2)
    }
}