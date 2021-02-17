package com.personal.todolist.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_list")
data class TodoListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
)
