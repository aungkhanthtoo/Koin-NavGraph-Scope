package com.example.koinnavscope.featureA.ui.screenA1

import com.example.koinnavscope.nav.KoinNavGraphBuilder
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
internal object ScreenA1

internal fun KoinNavGraphBuilder.screenA1(
    onNavigateBack: () -> Unit,
    onNavigateToScreenA2: () -> Unit
) {
    composable<ScreenA1> {
        ScreenA1(onNavigateBack, onNavigateToScreenA2, koinViewModel())
    }
}