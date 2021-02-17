package com.personal.todolist.domain.models

data class Task(
    val id: Long,
    val description: String,
    val complete: Boolean
)