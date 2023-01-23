package com.personal.todolist.ui.composable.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personal.todolist.common.createTodoList
import com.personal.todolist.ui.TodoListDetailState
import com.personal.todolist.ui.ui.theme.TodoListTheme
import com.personal.todolist.ui.viewModels.TodoListDetailViewModel

@Composable
fun TodoListDetailScreen(viewModel: TodoListDetailViewModel = hiltViewModel()) {
    val todoListUiState by viewModel.todoListDetailUiState.collectAsStateWithLifecycle()
    Surface(
        color = MaterialTheme.colors.background,
        contentColor = contentColorFor(MaterialTheme.colors.background)
    ) {
        TodoListDetailScreenContent(uiState = todoListUiState)
    }
    
}

@Composable
fun TodoListDetailScreenContent(uiState: TodoListDetailState) {
    when (uiState) {
        is TodoListDetailState.Error -> Snackbar(
            modifier = Modifier.padding(8.dp),
            backgroundColor = MaterialTheme.colors.error
        ) {
            Text(text = uiState.error)
        }
        is TodoListDetailState.Success -> Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = uiState.todoList.title,
                style = MaterialTheme.typography.h1
            )
        }
        TodoListDetailState.Loading -> Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TodoListDetailScreenPreview() {
    TodoListTheme {
        TodoListDetailScreenContent(
            uiState = TodoListDetailState.Success(createTodoList())
        )
    }
}