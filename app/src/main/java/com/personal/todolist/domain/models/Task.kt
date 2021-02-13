package com.personal.todolist.domain.models

data class Task(
    val id: Int,
    val todoListId: Long,
    val description: String,
    val complete: Boolean
)