package com.personal.todolist.data.repository

import com.personal.todolist.common.ROW_ID_NOT_INSERTED
import com.personal.todolist.data.dao.TodoListDao
import com.personal.todolist.data.mappers.toDomain
import com.personal.todolist.data.mappers.toEntity
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

    override suspend fun updateTodoList(todoListId: Long, title: String): Boolean {
        return withContext(ioDispatcher) {
            todoListDao.updateTodoList(todoListId = todoListId, title = title) != 0
        }
    }

    override suspend fun deleteTodoList(todoList: TodoList): Boolean {
        return withContext(ioDispatcher) {
            todoListDao.delete(todoList)
        }
    }

    override suspend fun addTaskToTodoList(todoListId: Long, taskDescription: String): Boolean {
        return withContext(ioDispatcher) {
            todoListDao.insert(
                Task(
                    description = taskDescription,
                    complete = false
                ).toEntity(todoListId = todoListId)
            ) != ROW_ID_NOT_INSERTED
        }
    }

    override suspend fun updateTaskDescription(taskId: Long, description: String): Boolean {
        return withContext(ioDispatcher) {
            todoListDao.updateTaskDescription(
                taskId = taskId,
                description = description
            ) != 0
        }
    }

    override suspend fun updateTaskCompletion(taskId: Long, complete: Boolean): Boolean {
        return withContext(ioDispatcher) {
            todoListDao.updateTaskCompletion(
                taskId = taskId,
                complete = complete
            ) != 0
        }
    }

    override suspend fun deleteTask(taskId: Long): Boolean {
        return withContext(ioDispatcher) {
            todoListDao.deleteTask(taskId = taskId) != 0
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