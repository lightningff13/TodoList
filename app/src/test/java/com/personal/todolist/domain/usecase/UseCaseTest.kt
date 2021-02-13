package com.personal.todolist.domain.usecase

import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class UseCaseTest {
    protected val todoList = mockkClass(TodoList::class)
    protected val todoListRepository: TodoListRepository = mockk()

    init {
        every { todoList.id } returns 1
    }

    @BeforeAll
    fun init(){
        clearMocks(todoList, todoListRepository)
    }
}