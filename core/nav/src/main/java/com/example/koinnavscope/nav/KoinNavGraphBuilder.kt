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
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.NavigatorProvider
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.ComposeNavigatorDestinationBuilder
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.compose.DialogNavigatorDestinationBuilder
import androidx.navigation.get
import com.example.koinnavscope.nav.navigator.BottomSheetNavigator
import com.example.koinnavscope.nav.navigator.BottomSheetNavigatorDestinationBuilder
import kotlin.reflect.KClass
import kotlin.reflect.KType

inline fun <reified T : Any> NavGraphBuilder.navigationScope(
    startDestination: Any,
    controller: NavController,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    builder: KoinNavGraphBuilder.() -> Unit
): Unit = addDestination(
    KoinNavGraphBuilder(
        provider,
        startDestination,
        T::class,
        typeMap,
        controller
    ).apply(builder).build().apply {
        deepLinks.forEach { deepLink -> addDeepLink(deepLink) }
    }
)

fun NavGraphBuilder.navigationScope(
    startDestination: String,
    route: String,
    controller: NavController,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    builder: KoinNavGraphBuilder.() -> Unit
) {
    addDestination(
        KoinNavGraphBuilder(
            provider,
            startDestination,
            route,
            controller
        ).apply(builder).build().apply {
            arguments.forEach { (argumentName, argument) -> addArgument(argumentName, argument) }
            deepLinks.forEach { deepLink -> addDeepLink(deepLink) }
        }
    )
}

/**
 * A custom navigation graph builder that **overrides standard navigation extension functions**
 * to enforce **Koin-scoped navigation**.
 *
 * ## Why?
 * - Standard Jetpack Compose navigation extensions are **not nav graph scope-aware**.
 * - This class **replaces** these extensions with **member functions** that integrate with
 *   [KoinNavGraphScope].
 *
 * ## Key Differences:
 * - 📌 **Scope-Aware Navigation** → Every destination is wrapped within a [KoinNavGraphScope].
 * - 📌 **Overrides Extension Functions** → Prevents accidental usage of invalid extension functions.
 */
class KoinNavGraphBuilder : NavGraphBuilder {

    val controller: NavController

    constructor(
        provider: NavigatorProvider,
        startDestination: Any,
        route: KClass<*>?,
        typeMap: Map<KType, @JvmSuppressWildcards NavType<*>>,
        controller: NavController
    ) : super(provider, startDestination, route, typeMap) {
        this.controller = controller
    }

    constructor(
        provider: NavigatorProvider,
        startDestination: String,
        route: String?,
        controller: NavController
    ) : super(provider, startDestination, route) {
        this.controller = controller
    }

    /**
     * Custom `navigation` function that replaces the standard extension function
     * [androidx.navigation.compose.navigation].
     * This member function **intentionally overrides** the existing extension function
     * to ensure that destinations added by [builder] render within the [KoinNavGraphScope].
     */
    inline fun <reified T : Any> navigation(
        startDestination: Any,
        typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
        builder: KoinNavGraphBuilder.() -> Unit
    ): Unit = destination(
        KoinNavGraphBuilder(
            provider,
            startDestination,
            T::class,
            typeMap,
            controller
        ).apply(builder)
    )

    /**
     * Custom `navigation` function that replaces the standard extension function
     * [androidx.navigation.compose.navigation].
     * This member function **intentionally overrides** the existing extension function
     * to ensure that destinations added by [builder] render within the [KoinNavGraphScope].
     */
    inline fun navigation(
        startDestination: String,
        route: String,
        builder: KoinNavGraphBuilder.() -> Unit
    ): Unit = destination(
        KoinNavGraphBuilder(
            provider,
            startDestination,
            route,
            controller
        ).apply(builder)
    )

    /**
     * Custom `composable` function that replaces the standard extension function
     * [androidx.navigation.compose.composable].
     * This member function **intentionally overrides** the existing extension function
     * to ensure that [content] renders within the [KoinNavGraphScope].
     */
    inline fun <reified T : Any> composable(
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
    ) {
        destination(
            ComposeNavigatorDestinationBuilder(
                provider[ComposeNavigator::class],
                T::class,
                typeMap
            ) {
                KoinNavGraphScope(it, controller) {
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
    }

    /**
     * Custom `composable` function that replaces the standard extension function
     * [androidx.navigation.compose.composable].
     * This member function **intentionally overrides** the existing extension function
     * to ensure that [content] renders within the [KoinNavGraphScope].
     */
    fun composable(
        route: String,
        arguments: List<NamedNavArgument> = emptyList(),
        deepLinks: List<NavDeepLink> = emptyList(),
        enterTransition:
        (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
            null,
        exitTransition:
        (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
            null,
        popEnterTransition:
        (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
            enterTransition,
        popExitTransition:
        (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
            exitTransition,
        sizeTransform:
        (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> SizeTransform?)? =
            null,
        content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
    ) {
        destination(
            ComposeNavigatorDestinationBuilder(
                provider[ComposeNavigator::class],
                route,
            ) {
                KoinNavGraphScope(it, controller) {
                    content(it)
                }
            }
                .apply {
                    arguments.forEach { (argumentName, argument) ->
                        argument(
                            argumentName,
                            argument
                        )
                    }
                    deepLinks.forEach { deepLink -> deepLink(deepLink) }
                    this.enterTransition = enterTransition
                    this.exitTransition = exitTransition
                    this.popEnterTransition = popEnterTransition
                    this.popExitTransition = popExitTransition
                    this.sizeTransform = sizeTransform
                }
        )
    }

    /**
     * Custom `dialog` function that replaces the standard extension function
     * [androidx.navigation.compose.dialog].
     * This member function **intentionally overrides** the existing extension function
     * to ensure that [content] renders within the [KoinNavGraphScope].
     */
    inline fun <reified T : Any> dialog(
        typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
        deepLinks: List<NavDeepLink> = emptyList(),
        dialogProperties: DialogProperties = DialogProperties(),
        noinline content: @Composable (NavBackStackEntry) -> Unit
    ) {
        destination(
            DialogNavigatorDestinationBuilder(
                provider[DialogNavigator::class],
                T::class,
                typeMap,
                dialogProperties,
            ) {
                KoinNavGraphScope(it, controller) {
                    content(it)
                }
            }
                .apply { deepLinks.forEach { deepLink -> deepLink(deepLink) } }
        )
    }

    /**
     * Custom `dialog` function that replaces the standard extension function
     * [androidx.navigation.compose.dialog].
     * This member function **intentionally overrides** the existing extension function
     * to ensure that [content] renders within the [KoinNavGraphScope].
     */
    fun dialog(
        route: String,
        arguments: List<NamedNavArgument> = emptyList(),
        deepLinks: List<NavDeepLink> = emptyList(),
        dialogProperties: DialogProperties = DialogProperties(),
        content: @Composable (NavBackStackEntry) -> Unit
    ) {
        destination(
            DialogNavigatorDestinationBuilder(
                provider[DialogNavigator::class],
                route,
                dialogProperties,
            ) {
                KoinNavGraphScope(it, controller) {
                    content(it)
                }
            }
                .apply {
                    arguments.forEach { (argumentName, argument) ->
                        argument(
                            argumentName,
                            argument
                        )
                    }
                    deepLinks.forEach { deepLink -> deepLink(deepLink) }
                }
        )
    }

    /**
     * Custom `bottomSheet` function that replaces the standard extension function
     * [com.example.koinnavscope.nav.navigator.bottomSheet].
     * This member function **intentionally overrides** the existing extension function
     * to ensure that [content] renders within the [KoinNavGraphScope].
     */
    inline fun <reified T : Any> bottomSheet(
        typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
        deepLinks: List<NavDeepLink> = emptyList(),
        noinline content: @Composable ColumnScope.(backstackEntry: NavBackStackEntry) -> Unit
    ) {
        destination(
            BottomSheetNavigatorDestinationBuilder(
                provider[BottomSheetNavigator::class],
                T::class,
                typeMap,
            ) {
                KoinNavGraphScope(it, controller) {
                    content(it)
                }
            }
                .apply {
                    deepLinks.fastForEach { deepLink -> deepLink(deepLink) }
                }
        )
    }

    /**
     * Custom `bottomSheet` function that replaces the standard extension function
     * [com.example.koinnavscope.nav.navigator.bottomSheet].
     * This member function **intentionally overrides** the existing extension function
     * to ensure that [content] renders within the [KoinNavGraphScope].
     */
    fun bottomSheet(
        route: String,
        arguments: List<NamedNavArgument> = emptyList(),
        deepLinks: List<NavDeepLink> = emptyList(),
        content: @Composable ColumnScope.(backstackEntry: NavBackStackEntry) -> Unit
    ) {
        destination(
            BottomSheetNavigatorDestinationBuilder(
                provider[BottomSheetNavigator::class],
                route,
            ) {
                KoinNavGraphScope(it, controller) {
                    content(it)
                }
            }
                .apply {
                    arguments.fastForEach { (argumentName, argument) ->
                        argument(argumentName, argument)
                    }
                    deepLinks.fastForEach { deepLink -> deepLink(deepLink) }
                }
        )
    }
}
