package com.personal.todolist.data.repository

import com.personal.todolist.domain.models.Task
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class FakeTodoListRepository : TodoListRepository {
    private val todoListsFlow = MutableSharedFlow<List<TodoList>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val currentTodoList get() = todoListsFlow.replayCache.firstOrNull() ?: emptyList()

    override suspend fun addTodoList(todoListTitle: String, tasks: List<Task>): Boolean =
        currentTodoList.let { current ->
            val todoList = TodoList(title = todoListTitle, tasks = tasks)
            val newTodoLists = current + todoList
            todoListsFlow.tryEmit(newTodoLists)
        }

    override suspend fun updateTodoList(todoList: TodoList): Boolean =
        currentTodoList.let { current ->
            val todoListToDelete = current.find { it.id == todoList.id }!!
            val newTodoLists = current - todoListToDelete + todoList
            todoListsFlow.tryEmit(newTodoLists)
        }


    override suspend fun deleteTodoList(todoList: TodoList): Boolean =
        currentTodoList.let { current ->
            val newTodoLists = current - todoList
            todoListsFlow.tryEmit(newTodoLists)
        }
    
    override fun getTodoListById(todoListId: Long): Flow<TodoList> = todoListsFlow.map { todoLists ->
        todoLists.find { it.id == todoListId }!!
    }
    override fun getTodoLists(): Flow<List<TodoList>> = todoListsFlow

    fun sendTodoLists(value: List<TodoList>) {
        todoListsFlow.tryEmit(value)
    }
}