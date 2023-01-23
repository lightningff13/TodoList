package com.personal.todolist.ui.navigation.destinations

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument

object TodoListDetailNavigationDestination : NavigationDestination {
    internal const val todoListIdArg = "todoListId"

    override val route: String
        get() = "todolist"
    override val navArguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(todoListIdArg) { type = NavType.LongType }
        )
}

fun NavController.navigateToTodoList(todoListId: Long) {
    this.navigate("${TodoListDetailNavigationDestination.route}/$todoListId")
}