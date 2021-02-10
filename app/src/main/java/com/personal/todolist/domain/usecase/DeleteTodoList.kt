package com.personal.todolist.domain.usecase

import com.personal.todolist.domain.repository.TodoListRepository

class DeleteTodoList(private val todoListRepository: TodoListRepository) : UseCase<Long, Boolean>{
    override suspend fun execute(params: Long): Boolean = todoListRepository.deleteTodoList(params)
}