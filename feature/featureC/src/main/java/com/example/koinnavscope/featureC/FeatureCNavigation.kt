package com.example.koinnavscope.featureC

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.koinnavscope.featureC.ui.screenC1.ScreenC1
import com.example.koinnavscope.featureC.ui.screenC1.screenC1
import com.example.koinnavscope.featureC.ui.screenC2.ScreenC2
import com.example.koinnavscope.featureC.ui.screenC2.navigateToScreenC2
import com.example.koinnavscope.featureC.ui.screenC2.screenC2
import kotlinx.serialization.Serializable

@Serializable
object FeatureC

@Serializable
object FeatureC2

fun NavGraphBuilder.featureCGraph(
    navController: NavController,
    onNavigateToScreenB2: () -> Unit,
) {
    navigation<FeatureC>(startDestination = ScreenC1) {
        screenC1(
            onNavigateBack = navController::popBackStack,
            onClickScreenC2 = navController::navigateToScreenC2
        )

        navigation<FeatureC2>(startDestination = ScreenC2) {
            screenC2(
                onNavigateBack = navController::popBackStack,
                onNavigateToScreenB2 = onNavigateToScreenB2
            )
        }
    }
}

fun NavController.navigateToFeatureC() {
    navigate(FeatureC)
}