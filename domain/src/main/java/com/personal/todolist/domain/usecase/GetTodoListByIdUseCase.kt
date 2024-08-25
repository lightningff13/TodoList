package com.personal.todolist.domain.usecase

import com.personal.todolist.common.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoListByIdUseCase @Inject constructor(private val todoListRepository: TodoListRepository) {
    fun execute(params: Long): Flow<TodoList> = todoListRepository.getTodoListById(params)
}