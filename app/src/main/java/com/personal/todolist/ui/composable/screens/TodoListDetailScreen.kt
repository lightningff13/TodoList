package com.personal.todolist.ui.composable.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
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
import com.personal.todolist.common.createTasks
import com.personal.todolist.common.createTodoList
import com.personal.todolist.ui.composable.todolist_detail.Task
import com.personal.todolist.ui.composable.todolist_detail.TaskToAdd
import com.personal.todolist.ui.composable.todolist_detail.TodoListTitle
import com.personal.todolist.ui.ui.theme.TodoListTheme
import com.personal.todolist.ui.viewModels.TodoListDetailState
import com.personal.todolist.ui.viewModels.TodoListDetailViewModel

@Composable
fun TodoListDetailScreen(viewModel: TodoListDetailViewModel = hiltViewModel()) {
    val todoListUiState by viewModel.todoListDetailUiState.collectAsStateWithLifecycle()
    Surface(
        color = MaterialTheme.colors.background,
        contentColor = contentColorFor(MaterialTheme.colors.background)
    ) {
        TodoListDetailScreenContent(
            uiState = todoListUiState,
            onTodoListTitleChanged = { todoListId, title ->
                viewModel.updateTodoListTitle(todoListId, title)
            },
            onTaskDescriptionChanged = { todoListId, taskDescription ->
                viewModel.addTaskToTodoList(todoListId, taskDescription)
            },
            onTaskDescriptionUpdated = { taskId, newDescription ->
                viewModel.updateTaskDescription(taskId, newDescription)
            },
            onTaskCompletionUpdated = { taskId, complete ->
                viewModel.updateTaskCompletion(taskId, complete)
            },
            onRemoveTaskClicked = { taskId ->
                viewModel.removeTask(taskId)
            }
        )
    }

}

@Composable
fun TodoListDetailScreenContent(
    uiState: TodoListDetailState,
    onTodoListTitleChanged: (todoListId: Long, title: String) -> Unit = { _, _ -> },
    onTaskDescriptionChanged: (Long, String) -> Unit = { _, _ -> },
    onTaskDescriptionUpdated: (taskId: Long, newDescription: String) -> Unit = { _, _-> },
    onTaskCompletionUpdated: (taskId: Long, complete: Boolean) -> Unit = { _, _-> },
    onRemoveTaskClicked: (Long) -> Unit = { }
) {
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                TodoListTitle(
                    title = uiState.todoList.title,
                    onTodoListTitleChanged = { onTodoListTitleChanged(uiState.todoList.id, it) }
                )
                Divider()
                LazyColumn {
                    items(items = uiState.todoList.tasks) {
                        Task(
                            task = it,
                            onDescriptionChanged = { newTaskDescription ->
                                onTaskDescriptionUpdated(
                                    it.id,
                                    newTaskDescription
                                )
                            },
                            onCompleteChanged = { newComplete ->
                                onTaskCompletionUpdated(it.id, newComplete)
                            },
                            onRemoveTaskClicked = { onRemoveTaskClicked(it.id) }
                        )
                    }
                    item {
                        TaskToAdd(
                            onValueChange = { taskDescription ->
                                onTaskDescriptionChanged(uiState.todoList.id, taskDescription)
                            }
                        )
                    }
                }
            }

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
            uiState = TodoListDetailState.Success(createTodoList(tasks = createTasks(20)))
        )
    }
}