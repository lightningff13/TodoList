package com.personal.todolist.domain.repository

import com.personal.todolist.data.entities.TodoListWithTasks

interface TodoListRepository {
    suspend fun addTodoList(todoList: TodoListWithTasks) : Boolean
    suspend fun updateTodoList(todoList: TodoListWithTasks) : Boolean
    suspend fun deleteTodoList(todoListId: Long) : Boolean
    suspend fun getTodoLists() : List<TodoListWithTasks>
}