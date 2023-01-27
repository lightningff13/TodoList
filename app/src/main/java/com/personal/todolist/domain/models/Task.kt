package com.personal.todolist.domain.models

data class Task(
    val id: Long = 0L,
    val description: String = "",
    val complete: Boolean = false
)