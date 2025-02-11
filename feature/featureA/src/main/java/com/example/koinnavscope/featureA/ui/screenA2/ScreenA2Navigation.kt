package com.example.koinnavscope.featureA.ui.screenA2

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navDeepLink
import com.example.koinnavscope.nav.composableNavScope
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
internal object ScreenA2

internal fun NavGraphBuilder.screenA2(
    navController: NavController,
    onNavigateBack: () -> Unit,
    onNavigateToFeatureB: () -> Unit,
) {
    composableNavScope<ScreenA2>(
        navController,
        deepLinks = listOf(navDeepLink {
            uriPattern = DEEPLINK_URI_A2
        })
    ) {
        ScreenA2(onNavigateBack, onNavigateToFeatureB, koinViewModel())
    }
}

fun NavController.navigateToScreenA2() = navigate(ScreenA2)

const val DEEPLINK_URI_A2 = "koin-app://featureA/a2"