package com.personal.todolist.domain.usecase

import com.personal.todolist.data.entities.TodoListWithTasks
import com.personal.todolist.domain.repository.TodoListRepository
import com.personal.todolist.domain.usecase.UseCase.None

class GetTodoLists(private val todoListRepository: TodoListRepository) :
    UseCase<None, List<TodoListWithTasks>> {
    override suspend fun execute(params: None): List<TodoListWithTasks> = todoListRepository.getTodoLists()
}