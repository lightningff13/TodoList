package com.personal.todolist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.todolist.common.STOP_TIMEOUT_MILLIS
import com.personal.todolist.common.models.TodoList
import com.personal.todolist.domain.usecase.CreateTodoListUseCase
import com.personal.todolist.domain.usecase.DeleteTodoListUseCase
import com.personal.todolist.domain.usecase.GetTodoListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListsViewModel @Inject constructor(
    getTodoLists: GetTodoListsUseCase,
    private val addTodoList: CreateTodoListUseCase,
    private val deleteTodoList: DeleteTodoListUseCase
) : ViewModel() {
    private val _todoListCreated =
        MutableSharedFlow<TodoListCreatedEvent>()
    val todoListCreated: SharedFlow<TodoListCreatedEvent> = _todoListCreated.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val todoListUiState: StateFlow<TodoListState> = getTodoLists
        .execute()
        .onEach { _isLoading.update { true } }
        .map { TodoListState.Success(todoLists = it) }
        .catch { TodoListState.Error(error = it.message ?: "An unexpected error occurred") }
        .onEach { _isLoading.update { false } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = TodoListState.Initial
        )

    fun deleteTodoList(todoList: TodoList) {
        viewModelScope.launch {
            deleteTodoList.execute(todoList).collect()
        }
    }

    fun addTodoList(todoListTitle: String) {
        viewModelScope.launch {
            addTodoList.execute(todoListTitle).catch {
                _todoListCreated.emit(
                    TodoListCreatedEvent.Error(
                        error = it.message ?: "Unexpected error during TodoList creation"
                    )
                )
            }.collect { todoListId ->
                _todoListCreated.emit(TodoListCreatedEvent.Success(todoListId = todoListId))
            }
        }
    }
}
