package com.personal.todolist.domain.usecase

import com.personal.todolist.common.Resource
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import com.personal.todolist.domain.usecase.UseCase.Parameters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTodoLists @Inject constructor(
    private val todoListRepository: TodoListRepository
    ) : UseCase<Parameters.None, Resource<List<TodoList>>> {
    override fun execute(params: Parameters.None): Flow<Resource<List<TodoList>>> = flow {
        try {
            emit(Resource.Loading(isLoading = true))
            val todoLists = todoListRepository.getTodoLists()
            emit(Resource.Success(data = todoLists))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        }
        emit(Resource.Loading(isLoading = false))
    }
}