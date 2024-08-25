package com.personal.todolist.ui.viewModels

import com.personal.todolist.common.models.TodoList

sealed class TodoListState {
    object Initial : TodoListState()
    data class Success(val todoLists: List<TodoList>) : TodoListState()
    data class Error(val error: String) : TodoListState()
}