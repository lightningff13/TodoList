package com.personal.todolist.common

import com.personal.todolist.data.entities.TaskEntity
import com.personal.todolist.data.entities.TodoListEntity
import com.personal.todolist.data.entities.TodoListWithTasks
import com.personal.todolist.data.mappers.toEntity
import com.personal.todolist.domain.models.Task
import com.personal.todolist.domain.models.TodoList

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

fun createTodoListWithTasks(
    todoListEntity: TodoListEntity = createTodoList().toEntity(),
    taskEntities: List<TaskEntity> = listOf(createTask().toEntity(1L))
) = TodoListWithTasks(
    todoListEntity = todoListEntity,
    taskEntities = taskEntities
)