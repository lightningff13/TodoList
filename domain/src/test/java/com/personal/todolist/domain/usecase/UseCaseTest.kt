package com.personal.todolist.domain.usecase

import com.personal.todolist.data.repository.LifecycleTest
import io.mockk.clearMocks
import io.mockk.mockk
import org.junit.jupiter.api.BeforeAll

abstract class UseCaseTest : LifecycleTest(){
    protected val todoListRepository: com.personal.todolist.domain.repository.TodoListRepository = mockk()

    @BeforeAll
    fun init(){
        clearMocks(todoListRepository)
    }
}