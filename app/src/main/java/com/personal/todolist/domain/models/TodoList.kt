package com.personal.todolist.domain.models

data class TodoList(
    val id: Long = 0L,
    val title: String,
    val tasks: List<Task>,
)