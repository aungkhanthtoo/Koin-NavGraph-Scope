package com.example.koinnavscope.koin

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.koinnavscope.featureA.FeatureA
import com.example.koinnavscope.featureA.ui.screenA2.DEEPLINK_URI_A2
import com.example.koinnavscope.featureC.FeatureC
import com.example.koinnavscope.featureC.ui.screenC1.DEEPLINK_URI_C1
import org.koin.androidx.compose.KoinAndroidContext

@Composable
fun KoinNavScope(
    navController: NavHostController,
    deepLinks: List<KoinNavDeepLink> = navDeepLinks,
    content: @Composable () -> Unit
) {
    KoinAndroidContext {
        KoinNavDeepLinkHandler(navController, deepLinks) {
            KoinNavSaveStateHandler(navController) {
                KoinNavGraphScope(navController, content)
            }
        }
    }
}

val navDeepLinks: List<KoinNavDeepLink> = listOf(
    KoinNavDeepLink(
        DEEPLINK_URI_A2,
        FeatureA
    ),
    KoinNavDeepLink(
        DEEPLINK_URI_C1,
        FeatureC
    ),
)