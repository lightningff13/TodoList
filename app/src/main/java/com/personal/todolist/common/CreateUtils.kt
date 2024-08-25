package com.personal.todolist.common

import com.personal.todolist.common.models.Task
import com.personal.todolist.common.models.TodoList

fun createTodoLists(size: Int): List<TodoList> {
    val todoLists = mutableListOf<TodoList>()
    for(i in 0..size){
        todoLists.add(createTodoList(id = i.toLong()))
    }
    return todoLists
}

fun createTodoList(
    id: Long = 1L,
    title: String = "Todo List",
    tasks: List<Task> = listOf(createTask())
) = TodoList(
    id = id,
    title = "$title $id",
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

fun createTasks(size: Int): List<Task>{
    val tasks = mutableListOf<Task>()
    for(i in 0..size){
        tasks.add(createTask(id = i.toLong()))
    }
    return tasks
}