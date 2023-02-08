package com.personal.todolist.ui.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.todolist.common.STOP_TIMEOUT_MILLIS
import com.personal.todolist.domain.usecase.AddTaskToTodoListUseCase
import com.personal.todolist.domain.usecase.DeleteTaskUseCase
import com.personal.todolist.domain.usecase.GetTodoListByIdUseCase
import com.personal.todolist.domain.usecase.UpdateTaskCompletionUseCase
import com.personal.todolist.domain.usecase.UpdateTaskDescriptionUseCase
import com.personal.todolist.domain.usecase.UpdateTodoListUseCase
import com.personal.todolist.ui.navigation.destinations.TodoListDetailNavigationDestination.todoListIdArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTodoListByIdUseCase: GetTodoListByIdUseCase,
    private val updateTodoListUseCase: UpdateTodoListUseCase,
    private val addTaskToTodoListUseCase: AddTaskToTodoListUseCase,
    private val updateTaskDescriptionUseCase: UpdateTaskDescriptionUseCase,
    private val updateTaskCompletionUseCase: UpdateTaskCompletionUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private val todoListId: Long = checkNotNull(
        savedStateHandle[todoListIdArg]
    )

    val todoListDetailUiState: StateFlow<TodoListDetailState> = getTodoListByIdUseCase
        .execute(todoListId)
        .map { TodoListDetailState.Success(todoList = it) }
        .catch { TodoListDetailState.Error(error = it.message ?: "An unexpected error occurred") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = TodoListDetailState.Loading
        )

    fun updateTodoListTitle(todoListId: Long, title: String) {
        viewModelScope.launch {
            updateTodoListUseCase.execute(
                params = UpdateTodoListUseCase.Params(
                    todoListId = todoListId,
                    title = title
                )
            ).collect()
        }
    }

    fun addTaskToTodoList(todoListId: Long, description: String) {
        viewModelScope.launch {
            addTaskToTodoListUseCase.execute(
                params = AddTaskToTodoListUseCase.Params(
                    todoListId = todoListId,
                    taskDescription = description
                )
            ).collect()
        }
    }

    fun updateTaskDescription(taskId: Long, description: String) {
        viewModelScope.launch {
            updateTaskDescriptionUseCase.execute(
                params = UpdateTaskDescriptionUseCase.Params(
                    taskId = taskId,
                    description = description
                )
            ).collect()
        }
    }

    fun updateTaskCompletion(taskId: Long, complete: Boolean) {
        viewModelScope.launch {
            updateTaskCompletionUseCase.execute(
                params = UpdateTaskCompletionUseCase.Params(
                    taskId = taskId,
                    complete = complete
                )
            ).collect()
        }
    }

    fun removeTask(taskId: Long) {
        viewModelScope.launch {
            deleteTaskUseCase.execute(
                params = DeleteTaskUseCase.Params(
                    taskId = taskId
                )
            ).collect()
        }
    }
}