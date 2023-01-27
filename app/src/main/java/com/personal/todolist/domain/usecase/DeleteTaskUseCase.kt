package com.personal.todolist.domain.usecase

import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val todoListRepository: TodoListRepository
) {
    data class Params (val taskId: Long)

    fun execute(params: Params): Flow<Boolean> = flow {
        emit(todoListRepository.deleteTask(taskId = params.taskId))
    }
}