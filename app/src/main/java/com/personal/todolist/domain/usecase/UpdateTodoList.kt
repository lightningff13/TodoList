package com.personal.todolist.domain.usecase

import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository

class UpdateTodoList(private val todoListRepository: TodoListRepository) :
    UseCase<TodoList, Boolean> {
    override suspend fun execute(params: TodoList): Boolean =
        todoListRepository.updateTodoList(params)
}