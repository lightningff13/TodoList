package com.personal.todolist.data.mappers

import com.personal.todolist.common.models.Task
import com.personal.todolist.common.models.TodoList
import com.personal.todolist.data.entities.TaskEntity
import com.personal.todolist.data.entities.TodoListEntity

fun TodoList.toEntity(): TodoListEntity {
    return TodoListEntity(
        id = id,
        title = title
    )
}

fun Task.toEntity(todoListId: Long): TaskEntity {
    return TaskEntity(
        id = id,
        todoListId = todoListId,
        description = description,
        complete = complete
    )
}