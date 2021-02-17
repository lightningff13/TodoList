package com.personal.todolist.domain.usecase

import com.personal.todolist.utils.createTodoList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import org.junit.Test


class DeleteTodoListTest : UseCaseTest(){
    private val deleteTodoList = DeleteTodoList((todoListRepository))

    init {
        coEvery { todoListRepository.deleteTodoList(any()) } returns true
    }

    @Test
    fun `should delete todo list with repository`() {
        val todoList = createTodoList()
        runBlocking {
            deleteTodoList.execute(todoList)
        }
        coVerify(exactly = 1) { todoListRepository.deleteTodoList(todoList) }
        confirmVerified(todoListRepository)
    }
}