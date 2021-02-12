package com.personal.todolist.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteTodoListTest : UseCaseTest(){
    private lateinit var deleteTodoList: DeleteTodoList

    @Before
    override fun setUp() {
        super.setUp()
        deleteTodoList = DeleteTodoList(todoListRepository)
        coEvery { todoListRepository.deleteTodoList(any()) } returns true
    }

    @Test
    fun `should delete todo list with repository`() {
        val todoListId = todoList.id
        runBlocking {
            deleteTodoList.execute(todoListId)
        }
        coVerify { todoListRepository.deleteTodoList(todoListId) }
        confirmVerified(todoListRepository)
    }
}