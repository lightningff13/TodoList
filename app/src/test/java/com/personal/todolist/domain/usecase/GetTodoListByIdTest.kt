package com.personal.todolist.domain.usecase

import com.personal.todolist.utils.createTodoList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetTodoListByIdTest : UseCaseTest(){
    private val getTodoListById = GetTodoListById(todoListRepository)

    init {
        coEvery { todoListRepository.getTodoListById(any()) } returns createTodoList()
    }

    @Test
    fun `should get todo list by id from repository`() {
        val todoListId = createTodoList().id
        runBlocking {
            getTodoListById.execute(todoListId)
        }
        coVerify(exactly = 1) { todoListRepository.getTodoListById(todoListId) }
        confirmVerified(todoListRepository)
    }
}