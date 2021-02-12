package com.personal.todolist.domain.models

data class Task(
    val id: Int,
    val todoListId: Long,
    val description: String,
    val complete: Boolean
){
    companion object {
        val empty = Task(0, 0L, "", false)
    }
}