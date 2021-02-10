package com.personal.todolist.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CreateTodoListTest : UseCaseTest(){
    private lateinit var createTodoList: CreateTodoList

    @Before
    override fun setUp() {
        super.setUp()
        createTodoList = CreateTodoList(todoListRepository)
        coEvery { todoListRepository.addTodoList(any()) } returns true
    }

    @Test
    fun execute_should_call_todoListRepository_add_method() {
        runBlocking {
            createTodoList.execute(todoListWithTasks)
        }
        coVerify(exactly = 1) { todoListRepository.addTodoList(todoListWithTasks) }
        confirmVerified(todoListRepository)
    }
}