package com.personal.todolist.domain.usecase

import com.google.common.truth.Truth
import com.personal.todolist.utils.createTodoList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class UpdateTodoListUseCaseTest : UseCaseTest() {
    private val updateTodoList = UpdateTodoListUseCase(todoListRepository)

    init {
        coEvery { todoListRepository.updateTodoList(any(), any()) } returns true
    }

    @Test
    fun `should update todo list with repository`() =
        runTest {
            val todoList = createTodoList()

            val elements =
                updateTodoList.execute(
                    params = UpdateTodoListUseCase.Params(
                        todoListId = todoList.id,
                        title = todoList.title
                    )
                ).toList()
            Truth.assertThat(elements.last()).isEqualTo(true)

            coVerify(exactly = 1) { todoListRepository.updateTodoList(todoList.id, todoList.title) }
            confirmVerified(todoListRepository)
        }
}