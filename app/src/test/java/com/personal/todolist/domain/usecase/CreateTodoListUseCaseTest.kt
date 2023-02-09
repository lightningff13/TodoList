package com.personal.todolist.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.personal.todolist.utils.createTodoList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test


@ExperimentalCoroutinesApi
class CreateTodoListUseCaseTest : UseCaseTest() {
    private val createTodoList = CreateTodoListUseCase(todoListRepository)

    init {
        coEvery { todoListRepository.addTodoList(any()) } returns 1L
    }

    @Test
    fun `should add todo list`() =
        runTest {
            val todoList = createTodoList()

            val elements = createTodoList.execute(todoList.title).toList()
            assertThat(elements.last()).isEqualTo(1L)

            coVerify(exactly = 1) { todoListRepository.addTodoList(todoList.title) }
            confirmVerified(todoListRepository)
        }
}