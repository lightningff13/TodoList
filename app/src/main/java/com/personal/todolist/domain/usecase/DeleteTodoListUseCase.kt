package com.personal.todolist.domain.usecase

import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteTodoListUseCase @Inject constructor(
    private val todoListRepository: TodoListRepository
) {
    fun execute(params: TodoList): Flow<Boolean> = flow {
        emit(todoListRepository.deleteTodoList(params))
    }
}
