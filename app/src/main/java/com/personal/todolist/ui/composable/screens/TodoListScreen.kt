package com.personal.todolist.ui.composable.screens

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personal.todolist.common.createTodoLists
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.ui.composable.todolists.CreateTodoListButton
import com.personal.todolist.ui.composable.todolists.TodoListsContent
import com.personal.todolist.ui.ui.theme.TodoListTheme
import com.personal.todolist.ui.viewModels.TodoListCreatedEvent
import com.personal.todolist.ui.viewModels.TodoListState
import com.personal.todolist.ui.viewModels.TodoListsViewModel


@Composable
fun TodoListsScreen(
    viewModel: TodoListsViewModel = hiltViewModel(),
    onTodoListClick: (Long) -> Unit = {}
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val todoListUiState by viewModel.todoListUiState.collectAsStateWithLifecycle()
    val todoListCreatedEvent by viewModel.todoListCreated.collectAsStateWithLifecycle(
        initialValue = TodoListCreatedEvent.Initial,
        lifecycle = lifecycle)

    val onDeleteTodoList: (TodoList) -> Unit = {
        viewModel.deleteTodoList(it)
    }
    val onAddTodoList: (String) -> Unit = {
        viewModel.addTodoList(it)
    }

    LaunchedEffect(key1 = todoListCreatedEvent){
        when (val currentTodoListCreated = todoListCreatedEvent) {
            is TodoListCreatedEvent.Error -> {}
            is TodoListCreatedEvent.Success -> onTodoListClick(currentTodoListCreated.todoListId)
            TodoListCreatedEvent.Initial -> Unit
        }
    }


    TodoListsScreenContent(
        uiState = todoListUiState,
        onTodoListClick = onTodoListClick,
        onDeleteTodoList = onDeleteTodoList,
        onAddClick = onAddTodoList
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TodoListsScreenContent(
    uiState: TodoListState,
    onTodoListClick: (Long) -> Unit = {},
    onDeleteTodoList: (TodoList) -> Unit = {},
    onAddClick: (String) -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            CreateTodoListButton(
                onCreateClick = onAddClick
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        TodoListsContent(
            todoListUiState = uiState,
            onTodoListClick = onTodoListClick,
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

