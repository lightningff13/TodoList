package com.personal.todolist.ui

import com.personal.todolist.domain.models.TodoList

data class TodoListState(
    val isLoading: Boolean = false,
    val todoLists: List<TodoList> = emptyList(),
    val error: String = ""
)
