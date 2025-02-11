package com.example.koinnavscope.featureC.ui.screenC1

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navDeepLink
import com.example.koinnavscope.nav.bottomSheetNavScope
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
internal object ScreenC1

internal fun NavGraphBuilder.screenC1(
    navController: NavController,
    onNavigateBack: () -> Unit,
    onClickScreenC2: () -> Unit
) {
    bottomSheetNavScope<ScreenC1>(
        navController,
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