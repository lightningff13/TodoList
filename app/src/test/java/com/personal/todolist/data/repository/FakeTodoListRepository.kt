package com.personal.todolist.data.repository

import android.util.Log
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.toList
import kotlin.jvm.Throws

class FakeTodoListRepository : TodoListRepository {
    private val todoListsFlow = MutableSharedFlow<List<TodoList>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override suspend fun addTodoList(todoList: TodoList): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodoList(todoList: TodoList): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodoList(todoList: TodoList): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getTodoListById(todoListId: Long): TodoList {
        TODO("Not yet implemented")
    }
    override fun getTodoLists(): Flow<List<TodoList>> = todoListsFlow

    fun sendTodoLists(value: List<TodoList>) {
        todoListsFlow.tryEmit(value)
    }

    @Throws
    suspend fun throwException(message: String) {
        todoListsFlow.onStart { throw Exception(message) }.toList()
    }
}