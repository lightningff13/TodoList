package com.personal.todolist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.personal.todolist.ui.composable.screens.TodoListDetailScreen
import com.personal.todolist.ui.composable.screens.TodoListsScreen
import com.personal.todolist.ui.navigation.destinations.TodoListDetailNavigationDestination
import com.personal.todolist.ui.navigation.destinations.TodoListsNavigationDestination
import com.personal.todolist.ui.navigation.destinations.navigateToTodoList

@Composable
fun TodoListNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = TodoListsNavigationDestination.route
    ) {
        composable(route = TodoListsNavigationDestination.route) {
            TodoListsScreen(
                onTodoListClick = { todoListId ->
                    navController.navigateToTodoList(todoListId)
                }
            )
        }
        composable(
            route = "${TodoListDetailNavigationDestination.route}/{${TodoListDetailNavigationDestination.todoListIdArg}}",
            arguments = TodoListDetailNavigationDestination.navArguments
        ) {
            TodoListDetailScreen()
        }
    }
}