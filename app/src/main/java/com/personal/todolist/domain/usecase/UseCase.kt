package com.personal.todolist.domain.usecase

import kotlinx.coroutines.flow.Flow

interface UseCase<in Parameter, Result> {
    suspend fun execute(params: Parameter) : Flow<Result>
    class None
}