package com.personal.todolist.domain.usecase

import com.personal.todolist.data.entities.TodoListWithTasks
import com.personal.todolist.domain.repository.TodoListRepository

class CreateTodoList(private val todoListRepository: TodoListRepository) :
    UseCase<TodoListWithTasks, Boolean> {
    override suspend fun execute(params: TodoListWithTasks): Boolean = todoListRepository.addTodoList(params)
}
