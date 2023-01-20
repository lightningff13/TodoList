package com.personal.todolist.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.todolist.common.STOP_TIMEOUT_MILLIS
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.usecase.DeleteTodoListUseCase
import com.personal.todolist.domain.usecase.GetTodoListsUseCase
import com.personal.todolist.ui.TodoListState
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
class TodoListsViewModel @Inject constructor(
    getTodoLists: GetTodoListsUseCase,
    private val deleteTodoList: DeleteTodoListUseCase
) : ViewModel() {


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
}
