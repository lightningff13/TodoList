package com.personal.todolist.domain.usecase

import com.personal.todolist.domain.repository.TodoListRepository
import io.mockk.clearMocks
import io.mockk.mockk
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class UseCaseTest {
    protected val todoListRepository: TodoListRepository = mockk()

    @BeforeAll
    fun init(){
        clearMocks(todoListRepository)
    }
}