package com.personal.todolist.data.repository

import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository

class TodoListRepositoryImpl() : TodoListRepository{
    override suspend fun addTodoList(todoList: TodoList): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodoList(todoList: TodoList): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodoList(todoListId: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getTodoLists(): List<TodoList> {
        TODO("Not yet implemented")
    }
}