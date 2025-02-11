package com.example.koinnavscope.featureC.ui.screenC2

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import kotlinx.serialization.Serializable

@Serializable
internal object ScreenC2

internal fun NavGraphBuilder.screenC2(
    onNavigateBack: () -> Unit,
    onNavigateToScreenB2: () -> Unit,
) {
    dialog<ScreenC2> {
        ScreenC2(onNavigateBack, onNavigateToScreenB2)
    }
}

fun NavController.navigateToScreenC2() = navigate(ScreenC2)
