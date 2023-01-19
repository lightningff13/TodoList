package com.personal.todolist.ui.composable.screens

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personal.todolist.common.createTodoLists
import com.personal.todolist.ui.TodoListState
import com.personal.todolist.ui.composable.todolists.AddTodoListButton
import com.personal.todolist.ui.composable.todolists.TodoListsContent
import com.personal.todolist.ui.ui.theme.TodoListTheme
import com.personal.todolist.ui.viewModels.GetTodoListsViewModel


@Composable
fun TodoListsScreen(viewModel: GetTodoListsViewModel = hiltViewModel()) {
    val todoListUiState by viewModel.todoListUiState.collectAsStateWithLifecycle()
    TodoListsScreenContent(uiState = todoListUiState)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TodoListsScreenContent(uiState: TodoListState) {
    Scaffold(
        floatingActionButton = {
            AddTodoListButton()
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        TodoListsContent(todoListUiState = uiState)
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TodoListScreenPreview() {
    TodoListTheme {
        TodoListsScreenContent(uiState = TodoListState.Success(createTodoLists(size = 20)))
    }
}

