package com.personal.todolist.domain.usecase

import com.personal.todolist.utils.createTodoList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import org.junit.Test


class CreateTodoListTest : UseCaseTest(){
    private val createTodoList = CreateTodoList(todoListRepository)

    init {
        coEvery { todoListRepository.addTodoList(any()) } returns true
    }

    @Test
    fun `should add todo list with repository`() {
        val todoList = createTodoList()
        runBlocking {
            createTodoList.execute(todoList)
        }
        coVerify(exactly = 1) { todoListRepository.addTodoList(todoList) }
        confirmVerified(todoListRepository)
    }
}