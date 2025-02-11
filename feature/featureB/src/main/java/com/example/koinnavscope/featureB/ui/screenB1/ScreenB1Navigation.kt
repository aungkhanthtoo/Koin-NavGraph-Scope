package com.example.koinnavscope.featureB.ui.screenB1

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.koinnavscope.nav.composableNavScope
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
internal object ScreenB1

internal fun NavGraphBuilder.screenB1(
    navController: NavController,
    onNavigateBack: () -> Unit,
    onNavigateToScreenC1: () -> Unit
) {
    composableNavScope<ScreenB1>(navController) {
        ScreenB1(onNavigateBack, onNavigateToScreenC1, koinViewModel())
    }
}