package com.personal.todolist.utils

import com.personal.todolist.domain.models.Task
import com.personal.todolist.domain.models.TodoList

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
    id: Int = 1,
    todoListId : Long = 1L,
    description: String = "Description of the task",
    complete: Boolean = false
) = Task(
    id = id,
    todoListId = todoListId,
    description = description,
    complete = complete
)