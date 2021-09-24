package com.personal.todolist.domain.usecase

import android.database.sqlite.SQLiteException
import com.personal.todolist.common.Resource
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTodoListById(private val todoListRepository: TodoListRepository) :
    UseCase<Long, Resource<TodoList>> {
    override suspend fun execute(params: Long): Flow<Resource<TodoList>> = flow {
        try {
            emit(Resource.Loading())
            val todoList = todoListRepository.getTodoListById(params)
            emit(Resource.Success(todoList))
        } catch (e: SQLiteException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }

    }
}