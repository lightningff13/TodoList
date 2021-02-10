package com.personal.todolist.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_list")
data class TodoList(
    @PrimaryKey
    val id: Long,
    val title: String,
){
    companion object {
        val empty = TodoList(0, "")
    }
}
