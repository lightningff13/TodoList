package com.personal.todolist.data.repository

import com.personal.todolist.data.dao.TodoListDao
import com.personal.todolist.data.mappers.toDomain
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository

class TodoListRepositoryImpl(private val todoListDao: TodoListDao) : TodoListRepository {
    override suspend fun addTodoList(todoList: TodoList): Boolean = todoListDao.insert(todoList)

    override suspend fun updateTodoList(todoList: TodoList): Boolean = todoListDao.insert(todoList)

    override suspend fun deleteTodoList(todoList: TodoList): Boolean = todoListDao.delete(todoList)

    override suspend fun getTodoListById(todoListId: Long): TodoList =
        todoListDao.getById(todoListId).toDomain()

    override suspend fun getTodoLists(): List<TodoList> = todoListDao.getAll().map { it.toDomain() }
}