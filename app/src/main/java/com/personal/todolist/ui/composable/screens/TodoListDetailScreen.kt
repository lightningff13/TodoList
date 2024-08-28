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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    onTaskDescriptionChanged: (taskId: Long, newDescription: String) -> Unit = { _, _ -> },
    onTaskDescriptionUpdated: (taskId: Long, newDescription: String) -> Unit = { _, _ -> },
    onTaskCompletionUpdated: (taskId: Long, complete: Boolean) -> Unit = { _, _ -> },
    onRemoveTaskClicked: (taskId: Long) -> Unit = { }
) {
    val onDescriptionChanged = { taskId: Long ->
        { newTaskDescription: String ->
            onTaskDescriptionUpdated(taskId, newTaskDescription)
        }
    }

    val onCompleteChanged = { taskId: Long ->
        { newComplete: Boolean ->
            onTaskCompletionUpdated(taskId, newComplete)
        }
    }

    val onRemoveClicked = { taskId: Long ->
        { onRemoveTaskClicked(taskId) }
    }

    when (uiState) {
        is TodoListDetailState.Error -> Snackbar(
            modifier = Modifier.padding(8.dp),
            backgroundColor = MaterialTheme.colors.error
        ) {
            Text(text = uiState.error)
        }

        is TodoListDetailState.Success -> TaskList(
            uiState,
            onTodoListTitleChanged,
            onDescriptionChanged,
            onCompleteChanged,
            onRemoveClicked,
            onTaskDescriptionChanged
        )

        TodoListDetailState.Loading -> Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun TaskList(
    uiState: TodoListDetailState.Success,
    onTodoListTitleChanged: (todoListId: Long, title: String) -> Unit,
    onDescriptionChanged: (Long) -> (String) -> Unit,
    onCompleteChanged: (Long) -> (Boolean) -> Unit,
    onRemoveClicked: (Long) -> () -> Unit,
    onTaskDescriptionChanged: (taskId: Long, newDescription: String) -> Unit
) {
    Column(
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
            val (unCompletedTasks, completedTasks) = uiState.todoList.tasks.partition {
                !it.complete
            }

            var previousListSize by remember { mutableIntStateOf(0) }
            var requestFocus by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = unCompletedTasks.size) {
                if (unCompletedTasks.size > previousListSize) {
                    requestFocus = true
                }
                previousListSize = unCompletedTasks.size
            }
            LazyColumn {
                items(items = unCompletedTasks, key = { it.id }) {
                    Task(
                        task = it,
                        onDescriptionChanged = onDescriptionChanged(it.id),
                        onCompleteChanged = onCompleteChanged(it.id),
                        onRemoveTaskClicked = onRemoveClicked(it.id),
                        onFocusRequested = { requestFocus = false },
                        onImeActionDone = { taskDescription ->
                            onTaskDescriptionChanged(uiState.todoList.id, taskDescription)
                        },
                        requestFocus = requestFocus
                    )
                }

                item {
                    TaskToAdd(
                        onValueChange = { taskDescription ->
                            onTaskDescriptionChanged(uiState.todoList.id, taskDescription)
                        }
                    )
                }
                if (completedTasks.any() && unCompletedTasks.any()) {
                    item { Divider() }
                }
                items(items = completedTasks, key = { it.id }) {
                    Task(
                        task = it,
                        fieldsEnabled = false,
                        onDescriptionChanged = onDescriptionChanged(it.id),
                        onCompleteChanged = onCompleteChanged(it.id),
                        onRemoveTaskClicked = onRemoveClicked(it.id)
                    )
                }

            }
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