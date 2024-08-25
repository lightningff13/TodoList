package com.personal.todolist.data.mappers

import com.personal.todolist.common.models.Task
import com.personal.todolist.common.models.TodoList
import com.personal.todolist.data.entities.TaskEntity
import com.personal.todolist.data.entities.TodoListWithTasks

fun TaskEntity.toDomain(): Task =
    Task(
        id = id,
        description = description,
        complete = complete
    )

fun TodoListWithTasks.toDomain(): TodoList =
    TodoList(
        id = todoListEntity.id,
        title = todoListEntity.title,
        tasks = taskEntities.map { it.toDomain() }
    )