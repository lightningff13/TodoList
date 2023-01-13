package com.personal.todolist.domain.usecase

import android.database.sqlite.SQLiteException
import com.personal.todolist.common.Resource
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateTodoList @Inject constructor(
    private val todoListRepository: TodoListRepository
) {
    fun execute(params: TodoList): Flow<Resource<Boolean>> = flow {
        try {
            val isTodoListAdded = todoListRepository.addTodoList(params)
            emit(Resource.Success(isTodoListAdded))
        } catch (e: SQLiteException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}
