package com.personal.todolist.domain.usecase

import kotlinx.coroutines.flow.Flow

interface UseCase<in Parameter, Result> {
    fun execute(params: Parameter) : Flow<Result>
    sealed class Parameters {
        object None
    }
}
