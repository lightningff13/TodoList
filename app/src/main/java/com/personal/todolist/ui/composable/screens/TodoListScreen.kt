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
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.ui.TodoListState
import com.personal.todolist.ui.composable.todolists.AddTodoListButton
import com.personal.todolist.ui.composable.todolists.TodoListsContent
import com.personal.todolist.ui.ui.theme.TodoListTheme
import com.personal.todolist.ui.viewModels.TodoListsViewModel


@Composable
fun TodoListsScreen(viewModel: TodoListsViewModel = hiltViewModel()) {
    val todoListUiState by viewModel.todoListUiState.collectAsStateWithLifecycle()
    val onDeleteTodoList: (TodoList) -> Unit = {
        viewModel.deleteTodoList(it)
    }
    TodoListsScreenContent(
        uiState = todoListUiState,
        onDeleteTodoList = onDeleteTodoList
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TodoListsScreenContent(
    uiState: TodoListState,
    onDeleteTodoList: (TodoList) -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            AddTodoListButton()
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        TodoListsContent(
            todoListUiState = uiState,
            onDeleteTodoList = onDeleteTodoList
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TodoListScreenPreview() {
    TodoListTheme {
        TodoListsScreenContent(
            uiState = TodoListState.Success(createTodoLists(size = 20))
        )
    }
}

