package com.example.koinnavscope.featureB

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.koinnavscope.featureB.ui.screenB1.ScreenB1
import com.example.koinnavscope.featureB.ui.screenB1.screenB1
import com.example.koinnavscope.featureB.ui.screenB2.ScreenB2
import com.example.koinnavscope.featureB.ui.screenB2.screenB2
import com.example.koinnavscope.nav.navigation
import com.example.koinnavscope.nav.navigationKoinScope
import kotlinx.serialization.Serializable

@Serializable
object FeatureB

@Serializable
object FeatureB2

fun NavGraphBuilder.featureBGraph(
    navController: NavController,
    onNavigateToScreenC1: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    navigationKoinScope<FeatureB>(startDestination = ScreenB1, navController) {
        screenB1(
            onNavigateBack = navController::popBackStack,
            onNavigateToScreenC1 = onNavigateToScreenC1
        )

        navigation<FeatureB2>(startDestination = ScreenB2) {
            screenB2(
                onNavigateBack = navController::popBackStack,
                onNavigateToHome = onNavigateToHome
            )
        }
    }
}

fun NavController.navigateToFeatureB() {
    navigate(FeatureB)
}