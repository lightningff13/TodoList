package com.personal.todolist.domain.usecase

import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository

class GetTodoListById(private val todoListRepository: TodoListRepository) :
    UseCase<Long, TodoList> {
    override suspend fun execute(params: Long): TodoList =
        todoListRepository.getTodoListById(params)
}