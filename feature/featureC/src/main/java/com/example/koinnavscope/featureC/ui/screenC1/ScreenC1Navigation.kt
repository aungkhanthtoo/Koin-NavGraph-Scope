package com.example.koinnavscope.featureC.ui.screenC1

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navDeepLink
import com.example.koinnavscope.nav.navigator.bottomSheet
import kotlinx.serialization.Serializable

@Serializable
internal object ScreenC1

internal fun NavGraphBuilder.screenC1(
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
        ScreenC1(onNavigateBack, onClickScreenC2)
    }
}

const val DEEPLINK_URI_C1 = "koin-app://featureC/c1"