package com.personal.todolist.domain.usecase

import com.personal.todolist.common.Resource
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTodoLists @Inject constructor(
    private val todoListRepository: TodoListRepository
    ) {
    fun execute(): Flow<Resource<List<TodoList>>> = flow {
        try {
            val todoLists = todoListRepository.getTodoLists()
            emit(Resource.Success(data = todoLists))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}