package com.personal.todolist.domain.usecase

import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateTodoListUseCase @Inject constructor(private val todoListRepository: TodoListRepository) {
    data class Params(val todoListId: Long, val title: String)

    fun execute(params: Params): Flow<Boolean> = flow {
        emit(
            todoListRepository.updateTodoList(
                todoListId = params.todoListId,
                title = params.title
            )
        )
    }
}