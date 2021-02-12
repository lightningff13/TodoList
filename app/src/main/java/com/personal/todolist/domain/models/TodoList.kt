package com.personal.todolist.domain.models

data class TodoList(
    val id: Long,
    val title: String,
    val tasks: List<Task>,
){
    companion object {
        val empty = TodoList(0, "", listOf(Task.empty))
    }
}