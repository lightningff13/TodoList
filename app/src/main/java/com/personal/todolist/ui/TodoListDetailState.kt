package com.personal.todolist.ui

import com.personal.todolist.domain.models.TodoList

sealed class TodoListDetailState {
    object Loading : TodoListDetailState()
    data class Success(val todoList: TodoList) : TodoListDetailState()
    data class Error(val error: String) : TodoListDetailState()
}