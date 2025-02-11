package com.example.koinnavscope

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.get
import com.example.koinnavscope.featureA.featureAGraph
import com.example.koinnavscope.featureA.navigateToFeatureA
import com.example.koinnavscope.featureB.featureBGraph
import com.example.koinnavscope.featureB.navigateToFeatureB
import com.example.koinnavscope.featureB.ui.screenB2.navigateToScreenB2
import com.example.koinnavscope.featureC.FeatureC
import com.example.koinnavscope.featureC.featureCGraph
import com.example.koinnavscope.featureC.navigateToFeatureC
import com.example.koinnavscope.nav.navigator.BottomSheetNavigator
import com.example.koinnavscope.nav.navigator.ModalBottomSheetHost
import com.example.koinnavscope.nav.navigator.rememberBottomSheetNavigator
import com.example.koinnavscope.ui.home.Home
import com.example.koinnavscope.ui.home.homeDestination
import com.example.koinnavscope.ui.theme.KoinNavScopeTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinInternalApi

@OptIn(KoinInternalApi::class)
@Composable
fun App() {
    KoinNavScopeTheme {
        val navController = rememberNavController(rememberBottomSheetNavigator())

        KoinAndroidContext {
            AppNavHost(navController) {
                // top level start destination
                homeDestination(
                    onNavigateToFeatureA = navController::navigateToFeatureA,
                    onNavigateToFeatureB = navController::navigateToFeatureB,
                    onNavigateToFeatureC = navController::navigateToFeatureC,
                )
                // nested feature graph A
                featureAGraph(
                    navController,
                    onNavigateToFeatureB = navController::navigateToFeatureB
                )
                // nested feature graph B
                featureBGraph(
                    navController,
                    onNavigateToScreenC1 = navController::navigateToFeatureC,
                    onNavigateToHome = {
                        navController.popBackStack(Home, inclusive = false)
                    }
                )
                // nested feature graph C
                featureCGraph(
                    navController,
                    onNavigateToScreenB2 = {
                        navController.navigateToScreenB2 {
                            popUpTo(FeatureC) {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppNavHost(
    navController: NavHostController,
    builder: NavGraphBuilder.() -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Home,
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
        },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
        },
        builder = builder
    )

    val bottomSheetNavigator =
        navController.navigatorProvider.get<Navigator<out NavDestination>>(BottomSheetNavigator.NAME)
                as? BottomSheetNavigator ?: return
    // Show any bottom sheet destinations
    ModalBottomSheetHost(
        bottomSheetNavigator, contentWindowInsets = { WindowInsets(0) },
        containerColor = MaterialTheme.colorScheme.surface
    )
}

