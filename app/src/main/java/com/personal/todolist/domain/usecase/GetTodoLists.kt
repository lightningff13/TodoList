package com.personal.todolist.domain.usecase

import com.personal.todolist.common.Resource
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTodoLists @Inject constructor(
    private val todoListRepository: TodoListRepository
    ) {
    fun execute(): Flow<List<TodoList>> = todoListRepository.getTodoLists()
}