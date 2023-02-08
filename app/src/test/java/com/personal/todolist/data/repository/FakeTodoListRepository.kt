package com.personal.todolist.data.repository

import com.personal.todolist.domain.models.Task
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class FakeTodoListRepository : TodoListRepository {
    private val todoListsFlow =
        MutableSharedFlow<List<TodoList>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val currentTodoLists get() = todoListsFlow.replayCache.firstOrNull() ?: emptyList()

    override suspend fun addTodoList(todoListTitle: String, tasks: List<Task>): Long =
        currentTodoLists.let { current ->
            val todoList = TodoList(title = todoListTitle, tasks = tasks)
            val newTodoLists = current + todoList
            todoListsFlow.tryEmit(newTodoLists)
            todoList.id
        }

    override suspend fun updateTodoList(todoListId: Long, title: String): Boolean =
        currentTodoLists.let { current ->
            val todoListToDelete = current.find { it.id == todoListId }!!
            val newTodoLists = current - todoListToDelete + todoListToDelete.copy(title = title)
            todoListsFlow.tryEmit(newTodoLists)
        }

    override suspend fun deleteTodoList(todoList: TodoList): Boolean =
        currentTodoLists.let { current ->
            val newTodoLists = current - todoList
            todoListsFlow.tryEmit(newTodoLists)
        }

    override suspend fun addTaskToTodoList(todoListId: Long, taskDescription: String): Boolean =
        currentTodoLists.let { current ->
            val todoListToDelete = current.find { it.id == todoListId }!!
            val newTodoLists = current - todoListToDelete + todoListToDelete.copy(
                tasks = todoListToDelete.tasks + Task(description = taskDescription)
            )
            todoListsFlow.tryEmit(newTodoLists)
        }

    override suspend fun updateTaskDescription(taskId: Long, description: String): Boolean =
        currentTodoLists.let { current ->
            val todoListToDelete = current.find { todoList ->
                todoList.tasks.contains(
                    todoList.tasks.find { task -> task.id == taskId }!!
                )
            }!!
            val taskToDelete = todoListToDelete.tasks.find { it.id == taskId }!!
            val newTodoLists = current - todoListToDelete + todoListToDelete.copy(
                tasks = todoListToDelete.tasks - taskToDelete + taskToDelete.copy(
                    description = description
                )
            )
            todoListsFlow.tryEmit(newTodoLists)
        }

    override suspend fun updateTaskCompletion(taskId: Long, complete: Boolean): Boolean =
        currentTodoLists.let { current ->
            val todoListToDelete = current.find { todoList ->
                todoList.tasks.contains(
                    todoList.tasks.find { task -> task.id == taskId }!!
                )
            }!!
            val taskToDelete = todoListToDelete.tasks.find { it.id == taskId }!!
            val newTodoLists = current - todoListToDelete + todoListToDelete.copy(
                tasks = todoListToDelete.tasks - taskToDelete + taskToDelete.copy(
                    complete = complete
                )
            )
            todoListsFlow.tryEmit(newTodoLists)
        }

    override suspend fun deleteTask(taskId: Long): Boolean = currentTodoLists.let { current ->
        val todoListToDelete = current.find { todoList ->
            todoList.tasks.contains(
                todoList.tasks.find { task -> task.id == taskId }!!
            )
        }!!
        val newTodoLists =
            current - todoListToDelete + todoListToDelete.copy(tasks = todoListToDelete.tasks.filter { it.id != taskId })
        todoListsFlow.tryEmit(newTodoLists)
    }

    override fun getTodoListById(todoListId: Long): Flow<TodoList> =
        todoListsFlow.map { todoLists ->
            todoLists.find { it.id == todoListId }!!
        }

    override fun getTodoLists(): Flow<List<TodoList>> = todoListsFlow

    fun sendTodoLists(value: List<TodoList>) {
        todoListsFlow.tryEmit(value)
    }
}