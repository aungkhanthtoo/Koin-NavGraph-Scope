package com.example.koinnavscope.featureA.ui.screenA2

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import kotlinx.serialization.Serializable

@Serializable
internal object ScreenA2

internal fun NavGraphBuilder.screenA2(
    onNavigateBack: () -> Unit,
    onNavigateToFeatureB: () -> Unit,
) {
    composable<ScreenA2>(
        deepLinks = listOf(navDeepLink {
            uriPattern = DEEPLINK_URI_A2
        })
    ) {
        ScreenA2(onNavigateBack, onNavigateToFeatureB)
    }
}

fun NavController.navigateToScreenA2() = navigate(ScreenA2)

const val DEEPLINK_URI_A2 = "koin-app://featureA/a2"