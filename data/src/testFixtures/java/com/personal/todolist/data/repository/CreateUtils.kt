package com.personal.todolist.data.repository

import com.personal.todolist.common.models.Task
import com.personal.todolist.common.models.TodoList
import com.personal.todolist.data.entities.TodoListEntity
import com.personal.todolist.data.entities.TaskEntity
import com.personal.todolist.data.entities.TodoListWithTasks
import com.personal.todolist.data.mappers.toEntity

fun createTodoList(
    id: Long = 1L,
    title: String = "Todo List",
    tasks: List<Task> = listOf(createTask())
) = TodoList(
    id = id,
    title = title,
    tasks = tasks
)

fun createTask(
    id: Long = 1L,
    description: String = "Description of the task",
    complete: Boolean = false
) = Task(
    id = id,
    description = description,
    complete = complete
)

fun createTodoListWithTasks(
    todoListEntity: TodoListEntity = createTodoList().toEntity(),
    taskEntities: List<TaskEntity> = listOf(createTask().toEntity(1L))
) = TodoListWithTasks(
    todoListEntity = todoListEntity,
    taskEntities = taskEntities
)