@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.koinnavscope.nav.navigator

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.navigation.FloatingWindow
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.NavigatorState
import androidx.navigation.compose.LocalOwnersProvider
import com.example.koinnavscope.nav.navigator.BottomSheetNavigator.Destination
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Create and remember a [BottomSheetNavigator]
 */
@Composable
fun rememberBottomSheetNavigator(
    skipPartiallyExpanded: Boolean = true,
): BottomSheetNavigator {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )

    return remember(sheetState) { BottomSheetNavigator(sheetState) }
}

/**
 * Navigator that drives a [SheetState] for use of [ModalBottomSheetHost]s
 * with the navigation library. Every destination using this Navigator must set a valid
 * [Composable] by setting it directly on an instantiated [Destination] or calling
 * [bottomSheet].
 *
 * **This navigator supports only one bottom sheet entry in the back stack.** Navigation to
 * subsequent bottom sheet destinations will be ignored.
 *
 * <b>The [ManageSheetState] [Composable] will always return the current entry of the back
 * stack.
 *
 * When the sheet is dismissed by the user, the [state]'s [NavigatorState.backStack] will be popped.
 *
 * The primary constructor is not intended for public use. Please refer to
 * [rememberBottomSheetNavigator] instead.
 *
 * @param sheetState The [SheetState] that the [BottomSheetNavigator] will use to
 * drive the sheet state
 */
@Navigator.Name("bottomSheet")
class BottomSheetNavigator(
    internal val sheetState: SheetState
) : Navigator<Destination>() {

    private var attached by mutableStateOf(false)

    /**
     * Get the back stack from the [state]. In some cases, the [ManageSheetState] might be
     * composed before the Navigator is attached, so we specifically return an empty flow if we
     * aren't attached yet.
     */
    private val backStack: StateFlow<List<NavBackStackEntry>>
        get() =
            if (attached) {
                state.backStack
            } else {
                MutableStateFlow(emptyList())
            }

    /**
     * Get the transitionsInProgress from the [state]. In some cases, the [ManageSheetState]
     * might be composed before the Navigator is attached, so we specifically return an empty flow
     * if we aren't attached yet.
     */
    private val transitionsInProgress: StateFlow<Set<NavBackStackEntry>>
        get() =
            if (attached) {
                state.transitionsInProgress
            } else {
                MutableStateFlow(emptySet())
            }

    @Composable
    internal fun ManageSheetState(
        content: @Composable (entry: NavBackStackEntry) -> Unit,
    ) {
        val saveableStateHolder = rememberSaveableStateHolder()

        val currentEntryState = produceState<NavBackStackEntry?>(
            initialValue = null,
            key1 = backStack
        ) {
            backStack.collect { entries ->
                val entry = entries.lastOrNull()
                val currentTransitionsInProgress = transitionsInProgress.value

                // Hide if popped with nav controller
                if (entry == null && sheetState.isVisible) {
                    try {
                        sheetState.hide()
                    } catch (_: CancellationException) {
                    }
                }

                currentTransitionsInProgress.forEach {
                    state.markTransitionComplete(it)
                }

                value = entry
            }
        }

        val currentEntry = currentEntryState.value ?: return

        currentEntry.LocalOwnersProvider(saveableStateHolder) {
            content(currentEntry)
        }
    }

    internal fun onDismissed(entry: NavBackStackEntry) {
        if (backStack.value.lastOrNull() == entry) {
            state.pop(popUpTo = entry, saveState = false)
        }
    }

    override fun onAttach(state: NavigatorState) {
        super.onAttach(state)
        attached = true
    }

    override fun createDestination(): Destination = Destination(this) {}

    override fun navigate(
        entries: List<NavBackStackEntry>,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ) {
        // Allow only one bottom sheet
        if (backStack.value.isNotEmpty()) {
            return
        }

        entries.firstOrNull()?.let {
            state.pushWithTransition(it)
        }
    }

    override fun popBackStack(popUpTo: NavBackStackEntry, savedState: Boolean) {
        state.popWithTransition(popUpTo, savedState)
    }

    /**
     * [NavDestination] specific to [BottomSheetNavigator].
     *
     * @param navigator The navigator used to navigate to this destination
     * @param content The content to be displayed for this destination
     */
    @NavDestination.ClassType(Composable::class)
    class Destination(
        navigator: BottomSheetNavigator,
        internal val content: @Composable ColumnScope.(NavBackStackEntry) -> Unit
    ) : NavDestination(navigator), FloatingWindow

    companion object {
        const val NAME = "bottomSheet"
    }
}