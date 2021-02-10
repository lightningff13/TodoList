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
    fun execute_should_call_todoListRepository_update_method() {
        runBlocking {
            updateTodoList.execute(todoListWithTasks)
        }
        coVerify(exactly = 1) { todoListRepository.updateTodoList(todoListWithTasks) }
        confirmVerified(todoListRepository)
    }
}