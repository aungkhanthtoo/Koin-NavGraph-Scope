package com.example.koinnavscope.ui.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Home

fun NavGraphBuilder.homeDestination(
    onNavigateToFeatureA: () -> Unit,
    onNavigateToFeatureB: () -> Unit,
    onNavigateToFeatureC: () -> Unit,
) {
    composable<Home> {
        HomeScreen(
            onNavigateToFeatureA = onNavigateToFeatureA,
            onNavigateToFeatureB = onNavigateToFeatureB,
            onNavigateToFeatureC = onNavigateToFeatureC,
        )
    }
}