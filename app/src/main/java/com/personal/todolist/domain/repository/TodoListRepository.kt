package com.personal.todolist.domain.repository

import com.personal.todolist.domain.models.TodoList

interface TodoListRepository {
    suspend fun addTodoList(todoList: TodoList) : Boolean
    suspend fun updateTodoList(todoList: TodoList) : Boolean
    suspend fun deleteTodoList(todoListId: Long) : Boolean
    suspend fun getTodoLists() : List<TodoList>
}