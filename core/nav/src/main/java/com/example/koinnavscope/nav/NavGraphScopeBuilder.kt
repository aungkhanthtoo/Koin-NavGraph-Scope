package com.example.koinnavscope.nav

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDestination
import androidx.navigation.NavDestinationBuilder
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.ComposeNavigatorDestinationBuilder
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.compose.DialogNavigatorDestinationBuilder
import androidx.navigation.get
import com.example.koinnavscope.nav.navigator.BottomSheetNavigator
import com.example.koinnavscope.nav.navigator.BottomSheetNavigatorDestinationBuilder
import kotlin.apply
import kotlin.reflect.KType

/**
 * A scope-limited builder interface for defining navigation destinations within a koin scoped nav graph.
 *
 * This interface ensures that only valid dsl functions are exposed when building a koin scoped navigation graph,
 * preventing access to standard extension functions of `NavGraphBuilder` like
 * [androidx.navigation.compose.navigation] since they are no longer invalid when using
 * koin scoped navigation.
 */
interface NavGraphScopeBuilder {

    val provider: NavGraphScopeProvider

    fun <D : NavDestination> destination(navDestination: NavDestinationBuilder<D>)
}

inline fun <reified T : Any> NavGraphBuilder.navigationKoinScope(
    startDestination: Any,
    controller: NavController,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    builder: NavGraphScopeBuilder.() -> Unit
): Unit = addDestination(
    KoinNavGraphBuilder(
        NavGraphBuilder(provider, startDestination, T::class, typeMap),
        NavGraphScopeProvider(controller)
    ).apply(builder).build().apply {
        deepLinks.forEach { deepLink -> addDeepLink(deepLink) }
    }
)

inline fun <reified T : Any> NavGraphScopeBuilder.navigation(
    startDestination: Any,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    noinline builder: NavGraphScopeBuilder.() -> Unit
) {
    val delegate = NavGraphBuilder(provider.navigatorProvider, startDestination, T::class, typeMap)
    KoinNavGraphBuilder(delegate, provider).apply(builder)
    destination(delegate)
}

inline fun <reified T : Any> NavGraphScopeBuilder.composable(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline enterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    EnterTransition?)? =
        null,
    noinline exitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    ExitTransition?)? =
        null,
    noinline popEnterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    EnterTransition?)? =
        enterTransition,
    noinline popExitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    ExitTransition?)? =
        exitTransition,
    noinline sizeTransform:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    SizeTransform?)? =
        null,
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) = destination(
    ComposeNavigatorDestinationBuilder(
        provider.navigatorProvider[ComposeNavigator::class],
        T::class,
        typeMap,
    ) {
        KoinNavGraphScope(it, provider::getNavScopeEntryOrNull) {
            content(it)
        }
    }
        .apply {
            deepLinks.forEach { deepLink -> deepLink(deepLink) }
            this.enterTransition = enterTransition
            this.exitTransition = exitTransition
            this.popEnterTransition = popEnterTransition
            this.popExitTransition = popExitTransition
            this.sizeTransform = sizeTransform
        }
)

inline fun <reified T : Any> NavGraphScopeBuilder.dialog(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    dialogProperties: DialogProperties = DialogProperties(),
    noinline content: @Composable (NavBackStackEntry) -> Unit
) = destination(
    DialogNavigatorDestinationBuilder(
        provider.navigatorProvider[DialogNavigator::class],
        T::class,
        typeMap,
        dialogProperties,
    ) {
        KoinNavGraphScope(it, provider::getNavScopeEntryOrNull) {
            content(it)
        }
    }
        .apply { deepLinks.forEach { deepLink -> deepLink(deepLink) } }
)

inline fun <reified T : Any> NavGraphScopeBuilder.bottomSheet(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline content: @Composable ColumnScope.(backstackEntry: NavBackStackEntry) -> Unit
) = destination(
    BottomSheetNavigatorDestinationBuilder(
        provider.navigatorProvider[BottomSheetNavigator::class],
        T::class,
        typeMap,
    ) {
        KoinNavGraphScope(it, provider::getNavScopeEntryOrNull) {
            content(it)
        }
    }
        .apply {
            deepLinks.fastForEach { deepLink -> deepLink(deepLink) }
        }
)