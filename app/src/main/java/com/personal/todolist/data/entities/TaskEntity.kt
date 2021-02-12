package com.personal.todolist.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey
    val id: Int,
    val todoListId: Long,
    val description: String,
    val complete: Boolean
)
