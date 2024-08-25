package com.personal.todolist.common.models

data class Task(
    val id: Long = 0L,
    val description: String = "",
    val complete: Boolean = false
)