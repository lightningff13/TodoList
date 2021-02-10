package com.personal.todolist.domain.usecase

import com.personal.todolist.data.entities.TodoListWithTasks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetTodoListsTest : UseCaseTest() {
    private lateinit var getTodoLists: GetTodoLists

    @Before
    override fun setUp() {
        super.setUp()
        getTodoLists = GetTodoLists(todoListRepository)
        coEvery { todoListRepository.getTodoLists() } returns listOf(TodoListWithTasks.empty)
    }

    @Test
    fun execute_should_call_todoListRepository_get_method() {
        runBlocking {
            getTodoLists.execute(UseCase.None())
        }
        coVerify(exactly = 1) { todoListRepository.getTodoLists() }
        confirmVerified(todoListRepository)
    }
}