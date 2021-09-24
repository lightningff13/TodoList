package com.personal.todolist.domain.usecase

import android.database.sqlite.SQLiteException
import com.personal.todolist.common.Resource
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateTodoList(private val todoListRepository: TodoListRepository) :
    UseCase<TodoList, Resource<Boolean>> {
    override suspend fun execute(params: TodoList): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val updatedTodoList = todoListRepository.updateTodoList(params)
            emit(Resource.Success(updatedTodoList))
        } catch (e: SQLiteException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

}