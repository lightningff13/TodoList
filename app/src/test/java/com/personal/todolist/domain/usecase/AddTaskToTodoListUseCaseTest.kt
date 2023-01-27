package com.personal.todolist.domain.usecase

import com.google.common.truth.Truth
import com.personal.todolist.utils.createTask
import com.personal.todolist.utils.createTodoList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class AddTaskToTodoListUseCaseTest: UseCaseTest() {
    private val addTaskToTodoList = AddTaskToTodoListUseCase(todoListRepository)

    init {
        coEvery { todoListRepository.addTaskToTodoList(any(), any()) } returns true
    }

    @Test
    fun `should add task to todo list`() =
        runTest {
            val todoList = createTodoList()
            val task = createTask()

            val elements = addTaskToTodoList.execute(AddTaskToTodoListUseCase.Params(todoList.id, task.description)).toList()
            Truth.assertThat(elements.last()).isEqualTo(true)

            coVerify(exactly = 1) { todoListRepository.addTaskToTodoList(todoList.id, task.description) }
            confirmVerified(todoListRepository)
        }
}