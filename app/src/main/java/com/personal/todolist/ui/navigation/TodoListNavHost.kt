package com.personal.todolist.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.personal.todolist.ui.composable.screens.TodoListDetailScreen
import com.personal.todolist.ui.composable.screens.TodoListsScreen
import com.personal.todolist.ui.navigation.destinations.TodoListDetailNavigationDestination
import com.personal.todolist.ui.navigation.destinations.TodoListsNavigationDestination
import com.personal.todolist.ui.navigation.destinations.navigateToTodoList

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TodoListNavHost(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = TodoListsNavigationDestination.route,
        enterTransition = {
            expandVertically(
                animationSpec =
                spring(
                    stiffness = Spring.StiffnessLow,
                    visibilityThreshold = IntSize.VisibilityThreshold
                )
            )
        }
    ) {
        composable(
            route = TodoListsNavigationDestination.route
        ) {
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