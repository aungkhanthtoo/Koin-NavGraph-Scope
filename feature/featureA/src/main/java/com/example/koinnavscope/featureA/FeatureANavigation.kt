package com.example.koinnavscope.featureA

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.koinnavscope.featureA.ui.screenA1.ScreenA1
import com.example.koinnavscope.featureA.ui.screenA1.screenA1
import com.example.koinnavscope.featureA.ui.screenA2.ScreenA2
import com.example.koinnavscope.featureA.ui.screenA2.navigateToScreenA2
import com.example.koinnavscope.featureA.ui.screenA2.screenA2
import kotlinx.serialization.Serializable

@Serializable
object FeatureA

@Serializable
object FeatureA2

fun NavGraphBuilder.featureAGraph(
    navController: NavController,
    onNavigateToFeatureB: () -> Unit,
) {
    navigation<FeatureA>(startDestination = ScreenA1) {
        screenA1(
            onNavigateBack = navController::popBackStack,
            onNavigateToScreenA2 = navController::navigateToScreenA2
        )

        navigation<FeatureA2>(startDestination = ScreenA2) {
            screenA2(navController::popBackStack, onNavigateToFeatureB)
        }
    }
}

fun NavController.navigateToFeatureA() {
    navigate(FeatureA)
}