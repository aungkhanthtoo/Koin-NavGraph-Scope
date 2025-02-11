package com.example.koinnavscope.featureA.ui.screenA1

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.koinnavscope.nav.composableNavScope
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
internal object ScreenA1

internal fun NavGraphBuilder.screenA1(
    navController: NavController,
    onNavigateBack: () -> Unit,
    onNavigateToScreenA2: () -> Unit
) {
    composableNavScope<ScreenA1>(navController) {
        ScreenA1(onNavigateBack, onNavigateToScreenA2, koinViewModel())
    }
}