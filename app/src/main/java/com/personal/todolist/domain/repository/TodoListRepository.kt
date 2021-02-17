package com.personal.todolist.domain.repository

import com.personal.todolist.domain.models.TodoList

interface TodoListRepository {
    suspend fun addTodoList(todoList: TodoList) : Boolean
    suspend fun updateTodoList(todoList: TodoList) : Boolean
    suspend fun deleteTodoList(todoList: TodoList) : Boolean
    suspend fun getTodoListById(todoListId: Long): TodoList
    suspend fun getTodoLists() : List<TodoList>
}