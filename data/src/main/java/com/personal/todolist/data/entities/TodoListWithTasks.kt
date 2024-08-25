package com.personal.todolist.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class TodoListWithTasks(
        @Embedded
        val todoListEntity: TodoListEntity,
        @Relation(
                parentColumn = "id",
                entityColumn = "todoListId"
        )
        val taskEntities: List<TaskEntity>
)