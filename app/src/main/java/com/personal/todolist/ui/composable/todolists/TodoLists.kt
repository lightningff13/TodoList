package com.personal.todolist.ui.composable.todolists

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.personal.todolist.common.createTodoLists
import com.personal.todolist.common.models.TodoList
import com.personal.todolist.ui.composable.common.ShimmerContent
import com.personal.todolist.ui.ui.theme.TodoListTheme
import com.personal.todolist.ui.viewModels.TodoListState

@Composable
fun TodoListsContent(
    todoListUiState: TodoListState,
    isLoading: Boolean = false,
    onTodoListClick: (Long) -> Unit = {},
    onDeleteTodoList: (TodoList) -> Unit = {},
    fabHeightInDp: Dp = 0.dp
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
        TodoListState.Initial -> Unit
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
                LazyColumn(
                    modifier = Modifier.padding(10.dp),
                    contentPadding = PaddingValues(bottom = fabHeightInDp + 10.dp)
                ) {
                    items(items = todoListUiState.todoLists) {
                        ShimmerContent(
                            isLoading = isLoading,
                            contentDuringLoading = {
                               ShimmerTodoListSummary(modifier = Modifier.padding(10.dp))
                            },
                            contentAfterLoading = {
                                TodoListSummary(
                                    todoListTitle = it.title,
                                    taskList = it.tasks,
                                    onTodoListClick = { onTodoListClick(it.id) },
                                    onDeleteClick = { onDeleteTodoList(it) }
                                )
                            },
                        )
                    }
                }
            }
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