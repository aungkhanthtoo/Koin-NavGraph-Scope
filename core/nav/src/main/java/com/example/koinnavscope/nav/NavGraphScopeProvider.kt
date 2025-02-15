package com.example.koinnavscope.nav

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavigatorProvider

class NavGraphScopeProvider(private val controller: NavController) {

    val navigatorProvider: NavigatorProvider = controller.navigatorProvider

    fun getBackStackEntry(route: String): NavBackStackEntry {
        return controller.getBackStackEntry(route)
    }
}