package com.personal.todolist.domain.usecase

import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import com.personal.todolist.domain.usecase.UseCase.None

class GetTodoLists(private val todoListRepository: TodoListRepository) :
    UseCase<None, List<TodoList>> {
    override suspend fun execute(params: None): List<TodoList> = todoListRepository.getTodoLists()
}