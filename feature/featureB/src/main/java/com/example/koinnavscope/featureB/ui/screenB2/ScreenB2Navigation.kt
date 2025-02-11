package com.example.koinnavscope.featureB.ui.screenB2

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import com.example.koinnavscope.nav.composableNavScope
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object ScreenB2

internal fun NavGraphBuilder.screenB2(
    navController: NavController,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    composableNavScope<ScreenB2>(navController) {
        ScreenB2(onNavigateBack, onNavigateToHome, koinViewModel())
    }
}

fun NavController.navigateToScreenB2(builder: NavOptionsBuilder.() -> Unit) =
    navigate(ScreenB2, builder)
