package com.personal.todolist.ui.viewModels

import com.personal.todolist.common.models.TodoList

sealed class TodoListDetailState {
    object Loading : TodoListDetailState()
    data class Success(val todoList: TodoList) : TodoListDetailState()
    data class Error(val error: String) : TodoListDetailState()
}