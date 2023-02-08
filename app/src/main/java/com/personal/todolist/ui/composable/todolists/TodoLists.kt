package com.personal.todolist.ui.composable.todolists

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.personal.todolist.common.createTodoLists
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.ui.ui.theme.TodoListTheme
import com.personal.todolist.ui.viewModels.TodoListState

@Composable
fun TodoListsContent(
    todoListUiState: TodoListState,
    onTodoListClick: (Long) -> Unit = {},
    onDeleteTodoList: (TodoList) -> Unit = {}
) {
    when (todoListUiState) {
        is TodoListState.Error -> {
            Snackbar(
                modifier = Modifier.padding(8.dp),
                backgroundColor = MaterialTheme.colors.error
            ) {
                Text(text = todoListUiState.error)
            }
        }
        TodoListState.Loading -> Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
        is TodoListState.Success ->
            if (todoListUiState.todoLists.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No todolists added yet")
                }
            } else {
                LazyColumn(modifier = Modifier.padding(10.dp)) {
                    items(items = todoListUiState.todoLists) {
                        TodoListSummary(
                            todoListTitle = it.title,
                            taskList = it.tasks,
                            onTodoListClick = { onTodoListClick(it.id) },
                            onDeleteClick = { onDeleteTodoList(it) }
                        )
                    }
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListsLoadingPreview() {
    TodoListTheme {
        TodoListsContent(TodoListState.Loading)
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListsErrorPreview() {
    TodoListTheme {
        TodoListsContent(TodoListState.Error("Unexpected error happened!"))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TodoListsSuccessPreview() {
    TodoListTheme {
        TodoListsContent(TodoListState.Success(createTodoLists(size = 20)))
    }
}