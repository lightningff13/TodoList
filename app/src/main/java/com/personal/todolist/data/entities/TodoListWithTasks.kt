package com.personal.todolist.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class TodoListWithTasks(
        @Embedded
        val todoList: TodoList,
        @Relation(
                parentColumn = "id",
                entityColumn = "todoListId"
        )
        val tasks: List<Task>

){
        companion object {
                val empty = TodoListWithTasks(TodoList.empty, listOf(Task.empty))
        }
}