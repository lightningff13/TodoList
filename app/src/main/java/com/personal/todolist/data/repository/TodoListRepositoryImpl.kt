package com.personal.todolist.data.repository

import com.personal.todolist.data.dao.TodoListDao
import com.personal.todolist.data.mappers.toDomain
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TodoListRepositoryImpl(
    private val todoListDao: TodoListDao,
    private val ioDispatcher: CoroutineDispatcher) : TodoListRepository {
    override suspend fun addTodoList(todoList: TodoList): Boolean {
        return withContext(ioDispatcher){
             todoListDao.insert(todoList)
        }
    }

    override suspend fun updateTodoList(todoList: TodoList): Boolean {
        return withContext(ioDispatcher){
            todoListDao.insert(todoList)
        }
    }

    override suspend fun deleteTodoList(todoList: TodoList): Boolean {
        return withContext(ioDispatcher){
            todoListDao.delete(todoList)
        }
    }

    override suspend fun getTodoListById(todoListId: Long): TodoList {
        return withContext(ioDispatcher){
            todoListDao.getById(todoListId).toDomain()
        }
    }


    override suspend fun getTodoLists(): List<TodoList> {
        return withContext(ioDispatcher){
            todoListDao.getAll().map { it.toDomain() }
        }
    }
}