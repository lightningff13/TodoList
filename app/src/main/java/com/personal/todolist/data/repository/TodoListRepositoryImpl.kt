package com.personal.todolist.data.repository

import com.personal.todolist.data.dao.TodoListDao
import com.personal.todolist.data.mappers.toDomain
import com.personal.todolist.di.IoDispatcher
import com.personal.todolist.domain.models.Task
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoListRepositoryImpl @Inject constructor(
    private val todoListDao: TodoListDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : TodoListRepository {
    override suspend fun addTodoList(todoListTitle: String, tasks: List<Task>): Boolean {
        return withContext(ioDispatcher) {
            todoListDao.insert(
                TodoList(title = todoListTitle, tasks = tasks)
            )
        }
    }

    override suspend fun updateTodoList(todoList: TodoList): Boolean {
        return withContext(ioDispatcher) {
            todoListDao.insert(todoList)
        }
    }

    override suspend fun deleteTodoList(todoList: TodoList): Boolean {
        return withContext(ioDispatcher) {
            todoListDao.delete(todoList)
        }
    }

    override fun getTodoListById(todoListId: Long): Flow<TodoList> =
        todoListDao.getById(todoListId).map {
            it.toDomain()
        }

    override fun getTodoLists(): Flow<List<TodoList>> =
        todoListDao.getAll().map { todoListWithTasks ->
            todoListWithTasks.map { it.toDomain() }
        }
}