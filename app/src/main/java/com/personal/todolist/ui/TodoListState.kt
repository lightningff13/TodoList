package com.personal.todolist.ui

import com.personal.todolist.domain.models.TodoList

sealed class TodoListState {
    object Loading : TodoListState()
    data class Success(val todoLists: List<TodoList>) : TodoListState()
    data class Error(val error: String) : TodoListState()
}