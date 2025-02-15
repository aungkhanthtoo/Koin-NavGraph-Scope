package com.example.koinnavscope.featureC.ui.screenC2

import androidx.navigation.NavController
import com.example.koinnavscope.nav.NavGraphScopeBuilder
import com.example.koinnavscope.nav.dialog
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
internal object ScreenC2

internal fun NavGraphScopeBuilder.screenC2(
    onNavigateBack: () -> Unit,
    onNavigateToScreenB2: () -> Unit,
) {
    dialog<ScreenC2> {
        ScreenC2(onNavigateBack, onNavigateToScreenB2, koinViewModel())
    }
}

fun NavController.navigateToScreenC2() = navigate(ScreenC2)
