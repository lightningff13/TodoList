package com.personal.todolist.domain.usecase

import com.personal.todolist.utils.createTodoList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetTodoListsTest : UseCaseTest() {
    private val getTodoLists = GetTodoLists(todoListRepository)

    init {
        coEvery { todoListRepository.getTodoLists() } returns listOf(createTodoList())
    }

    @Test
    fun `should get list of todo list from repository`() {
        runBlocking {
            getTodoLists.execute(UseCase.None())
        }
        coVerify(exactly = 1) { todoListRepository.getTodoLists() }
        confirmVerified(todoListRepository)
    }
}