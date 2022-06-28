package com.personal.todolist.ui

import com.personal.todolist.domain.models.TodoList

sealed class TodoListState {
    data class Loading(val isLoading: Boolean) : TodoListState()
    data class Success(val todoLists: List<TodoList>) : TodoListState()
    data class Error(val error: String) : TodoListState()
}