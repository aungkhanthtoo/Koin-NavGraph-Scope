package com.example.koinnavscope.featureB.ui.screenB2

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object ScreenB2

internal fun NavGraphBuilder.screenB2(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    composable<ScreenB2> {
        ScreenB2(onNavigateBack, onNavigateToHome)
    }
}

fun NavController.navigateToScreenB2(builder: NavOptionsBuilder.() -> Unit) = navigate(ScreenB2, builder)
