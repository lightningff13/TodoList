package com.personal.todolist.domain.usecase

import android.database.sqlite.SQLiteException
import com.personal.todolist.common.Resource
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import com.personal.todolist.domain.usecase.UseCase.None
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTodoLists @Inject constructor(private val todoListRepository: TodoListRepository) :
    UseCase<None, Resource<List<TodoList>>> {
    override fun execute(params: None): Flow<Resource<List<TodoList>>> = flow {
        try {
            emit(Resource.Loading())
            val todoLists = todoListRepository.getTodoLists()
            emit(Resource.Success(todoLists))
        } catch (e: SQLiteException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}