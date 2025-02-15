package com.example.koinnavscope.featureC.ui.screenC1

import androidx.navigation.navDeepLink
import com.example.koinnavscope.nav.KoinNavGraphBuilder
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
internal object ScreenC1

internal fun KoinNavGraphBuilder.screenC1(
    onNavigateBack: () -> Unit,
    onClickScreenC2: () -> Unit
) {
    bottomSheet<ScreenC1>(
        deepLinks = listOf(
            navDeepLink {
                uriPattern = DEEPLINK_URI_C1
            }
        )
    ) {
        ScreenC1(onNavigateBack, onClickScreenC2, koinViewModel())
    }
}

const val DEEPLINK_URI_C1 = "koin-app://featureC/c1"