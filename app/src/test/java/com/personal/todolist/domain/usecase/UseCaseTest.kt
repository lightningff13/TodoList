package com.personal.todolist.domain.usecase

import com.personal.todolist.LifecycleTest
import com.personal.todolist.domain.repository.TodoListRepository
import io.mockk.clearMocks
import io.mockk.mockk
import org.junit.jupiter.api.BeforeAll

abstract class UseCaseTest : LifecycleTest(){
    protected val todoListRepository: TodoListRepository = mockk()

    @BeforeAll
    fun init(){
        clearMocks(todoListRepository)
    }
}