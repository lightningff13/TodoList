package com.personal.todolist.domain.usecase

import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateTaskDescriptionUseCase @Inject constructor(
    private val todoListRepository: TodoListRepository
) {
    data class Params(
        val taskId: Long,
        val description: String
    )

    fun execute(params: Params): Flow<Boolean> = flow {
        emit(
            todoListRepository.updateTaskDescription(
                taskId = params.taskId,
                description = params.description
            )
        )
    }
}