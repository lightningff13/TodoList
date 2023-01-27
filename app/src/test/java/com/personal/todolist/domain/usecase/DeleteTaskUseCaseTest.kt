package com.personal.todolist.domain.usecase

import com.google.common.truth.Truth
import com.personal.todolist.utils.createTask
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class DeleteTaskUseCaseTest : UseCaseTest() {
    private val deleteTaskUseCase = DeleteTaskUseCase(todoListRepository)

    init {
        coEvery { todoListRepository.deleteTask(any()) } returns true
    }

    @Test
    fun `should delete task`() =
        runTest {
            val task = createTask()

            val elements = deleteTaskUseCase.execute(DeleteTaskUseCase.Params(taskId = task.id)).toList()
            Truth.assertThat(elements.last()).isEqualTo(true)

            coVerify(exactly = 1) { todoListRepository.deleteTask(taskId = task.id) }
            confirmVerified(todoListRepository)
        }
}