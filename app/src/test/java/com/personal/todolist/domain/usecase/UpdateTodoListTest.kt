package com.personal.todolist.domain.usecase

import com.personal.todolist.utils.createTodoList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UpdateTodoListTest : UseCaseTest() {
    private val updateTodoList = UpdateTodoList(todoListRepository)

    init {
        coEvery { todoListRepository.updateTodoList(any()) } returns true
    }

    @Test
    fun `should update todo list with repository`() {
        val todoList = createTodoList()
        runBlocking {
            updateTodoList.execute(todoList)
        }
        coVerify(exactly = 1) { todoListRepository.updateTodoList(todoList) }
        confirmVerified(todoListRepository)
    }
}