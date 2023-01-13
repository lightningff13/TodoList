package com.personal.todolist.domain.usecase

import android.database.sqlite.SQLiteException
import com.personal.todolist.common.Resource
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTodoListById @Inject constructor(private val todoListRepository: TodoListRepository) {
    fun execute(params: Long): Flow<Resource<TodoList>> = flow {
        try {
            val todoList = todoListRepository.getTodoListById(params)
            emit(Resource.Success(todoList))
        } catch (e: SQLiteException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}