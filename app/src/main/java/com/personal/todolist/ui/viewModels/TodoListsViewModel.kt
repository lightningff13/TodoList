package com.personal.todolist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.todolist.common.STOP_TIMEOUT_MILLIS
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.usecase.CreateTodoListUseCase
import com.personal.todolist.domain.usecase.DeleteTodoListUseCase
import com.personal.todolist.domain.usecase.GetTodoListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    val todoListUiState: StateFlow<TodoListState> = getTodoLists
        .execute()
        .map { TodoListState.Success(todoLists = it) }
        .catch { TodoListState.Error(error = it.message ?: "An unexpected error occurred") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = TodoListState.Loading
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
