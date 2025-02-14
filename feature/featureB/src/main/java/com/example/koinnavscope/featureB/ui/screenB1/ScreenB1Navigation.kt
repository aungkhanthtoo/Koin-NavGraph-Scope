package com.example.koinnavscope.featureB.ui.screenB1

import com.example.koinnavscope.nav.KoinNavGraphBuilder
import com.example.koinnavscope.nav.composableNavScope
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
internal object ScreenB1

internal fun KoinNavGraphBuilder.screenB1(
    onNavigateBack: () -> Unit,
    onNavigateToScreenC1: () -> Unit
) {
    composableNavScope<ScreenB1> {
        ScreenB1(onNavigateBack, onNavigateToScreenC1, koinViewModel())
    }
}