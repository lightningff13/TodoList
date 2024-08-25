package com.personal.todolist.domain.usecase

import com.google.common.truth.Truth
import com.personal.todolist.data.repository.createTask
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class UpdateTaskUseCaseTest: UseCaseTest() {
    private val updateTaskDescriptionUseCase =
        UpdateTaskDescriptionUseCase(todoListRepository)
    private val updateTaskCompletionUseCase =
        UpdateTaskCompletionUseCase(todoListRepository)

    init {
        coEvery { todoListRepository.updateTaskDescription(any(), any()) } returns true
        coEvery { todoListRepository.updateTaskCompletion(any(), any()) } returns true
    }

    @Test
    fun `should update task description`() =
        runTest {
            val task = createTask()
            val newDescription = "newDescription"

            val elements = updateTaskDescriptionUseCase.execute(UpdateTaskDescriptionUseCase.Params(task.id, newDescription)).toList()
            Truth.assertThat(elements.last()).isEqualTo(true)

            coVerify(exactly = 1) { todoListRepository.updateTaskDescription(task.id, newDescription) }
            confirmVerified(todoListRepository)
        }

    @Test
    fun `should update task completion`() =
        runTest {
            val task = createTask()
            val newComplete = true

            val elements = updateTaskCompletionUseCase.execute(UpdateTaskCompletionUseCase.Params(task.id, newComplete)).toList()
            Truth.assertThat(elements.last()).isEqualTo(true)

            coVerify(exactly = 1) { todoListRepository.updateTaskCompletion(task.id, newComplete) }
            confirmVerified(todoListRepository)
        }
}