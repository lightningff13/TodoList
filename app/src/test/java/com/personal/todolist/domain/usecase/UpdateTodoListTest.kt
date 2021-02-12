package com.personal.todolist.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateTodoListTest : UseCaseTest() {
    private lateinit var updateTodoList: UpdateTodoList

    @Before
    override fun setUp() {
        super.setUp()
        updateTodoList = UpdateTodoList(todoListRepository)
        coEvery { todoListRepository.updateTodoList(any()) } returns true
    }

    @Test
    fun `should update todo list with repository`() {
        runBlocking {
            updateTodoList.execute(todoList)
        }
        coVerify(exactly = 1) { todoListRepository.updateTodoList(todoList) }
        confirmVerified(todoListRepository)
    }
}