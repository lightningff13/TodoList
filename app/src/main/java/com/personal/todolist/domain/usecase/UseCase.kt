package com.personal.todolist.domain.usecase

interface UseCase<in Parameter, out Result> {
    suspend fun execute(params: Parameter) : Result
    class None
}