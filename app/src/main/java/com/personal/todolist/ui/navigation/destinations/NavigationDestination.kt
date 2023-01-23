package com.personal.todolist.ui.navigation.destinations

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions

interface NavigationDestination {
    val route: String
    val navArguments: List<NamedNavArgument>
        get() = emptyList()

    fun NavController.navigateTo(navOptions: NavOptions = navOptions {}) =
        navigate(route, navOptions)
}