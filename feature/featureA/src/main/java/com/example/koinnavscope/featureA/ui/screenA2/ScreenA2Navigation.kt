package com.example.koinnavscope.featureA.ui.screenA2

import androidx.navigation.NavController
import androidx.navigation.navDeepLink
import com.example.koinnavscope.nav.KoinNavGraphBuilder
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
internal object ScreenA2

internal fun KoinNavGraphBuilder.screenA2(
    onNavigateBack: () -> Unit,
    onNavigateToFeatureB: () -> Unit,
) {
    composable<ScreenA2>(
        deepLinks = listOf(navDeepLink {
            uriPattern = DEEPLINK_URI_A2
        })
    ) {
        ScreenA2(onNavigateBack, onNavigateToFeatureB, koinViewModel())
    }
}

fun NavController.navigateToScreenA2() = navigate(ScreenA2)

const val DEEPLINK_URI_A2 = "koin-app://featureA/a2"