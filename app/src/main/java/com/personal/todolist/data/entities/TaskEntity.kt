package com.personal.todolist.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val todoListId: Long,
    val description: String,
    val complete: Boolean
)
