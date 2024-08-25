package com.personal.todolist.domain.repository

import com.personal.todolist.common.models.Task
import com.personal.todolist.common.models.TodoList
import kotlinx.coroutines.flow.Flow

interface TodoListRepository {
    suspend fun addTodoList(todoListTitle: String, tasks: List<Task> = emptyList()) : Long
    suspend fun updateTodoList(todoListId:Long, title: String) : Boolean
    suspend fun deleteTodoList(todoList: TodoList) : Boolean
    suspend fun addTaskToTodoList(todoListId: Long, taskDescription: String): Boolean
    suspend fun updateTaskDescription(taskId: Long, description: String): Boolean
    suspend fun updateTaskCompletion(taskId: Long, complete: Boolean): Boolean
    suspend fun deleteTask(taskId: Long): Boolean
    fun getTodoListById(todoListId: Long): Flow<TodoList>
    fun getTodoLists() : Flow<List<TodoList>>
}