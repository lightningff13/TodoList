package com.personal.todolist.domain.repository

import com.personal.todolist.domain.models.Task
import com.personal.todolist.domain.models.TodoList
import kotlinx.coroutines.flow.Flow

interface TodoListRepository {
    suspend fun addTodoList(todoListTitle: String, tasks: List<Task> = emptyList()) : Boolean
    suspend fun updateTodoList(todoList: TodoList) : Boolean
    suspend fun deleteTodoList(todoList: TodoList) : Boolean
    fun getTodoListById(todoListId: Long): Flow<TodoList>
    fun getTodoLists() : Flow<List<TodoList>>
}