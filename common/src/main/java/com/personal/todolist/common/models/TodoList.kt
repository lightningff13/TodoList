package com.personal.todolist.common.models

data class TodoList(
    val id: Long = 0L,
    val title: String,
    val tasks: List<Task>,
)