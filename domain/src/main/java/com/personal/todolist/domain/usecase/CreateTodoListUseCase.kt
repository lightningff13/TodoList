package com.personal.todolist.domain.usecase

import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateTodoListUseCase @Inject constructor(
    private val todoListRepository: TodoListRepository
) {
    fun execute(params: String): Flow<Long> = flow {
        emit(todoListRepository.addTodoList(params))
    }
}
