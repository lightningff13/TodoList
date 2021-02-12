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
    fun `should add todo list with repository`() {
        runBlocking {
            createTodoList.execute(todoList)
        }
        coVerify(exactly = 1) { todoListRepository.addTodoList(todoList) }
        confirmVerified(todoListRepository)
    }
}