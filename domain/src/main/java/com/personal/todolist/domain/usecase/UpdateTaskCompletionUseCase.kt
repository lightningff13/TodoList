package com.personal.todolist.domain.usecase

import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateTaskCompletionUseCase @Inject constructor(
    private val todoListRepository: TodoListRepository
) {
    data class Params(
        val taskId: Long,
        val complete: Boolean
    )

    fun execute(params: Params): Flow<Boolean> = flow {
        emit(
            todoListRepository.updateTaskCompletion(
                taskId = params.taskId,
                complete = params.complete
            )
        )
    }
}