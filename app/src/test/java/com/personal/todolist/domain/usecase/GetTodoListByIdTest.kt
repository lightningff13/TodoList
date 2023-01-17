package com.personal.todolist.domain.usecase

import android.database.sqlite.SQLiteException
import com.google.common.truth.Truth
import com.personal.todolist.common.Resource
import com.personal.todolist.utils.createTodoList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GetTodoListByIdTest : UseCaseTest() {
    private val getTodoListById = GetTodoListById(todoListRepository)

    init {
        coEvery { todoListRepository.getTodoListById(any()) } returns flowOf(createTodoList())
    }

    @Test
    fun `should returns a flow of todo list`() =
        runTest {
            val todoListId = createTodoList().id

            val elements = getTodoListById.execute(todoListId).toList()
            Truth.assertThat(elements.last()).isEqualTo(createTodoList())

            coVerify(exactly = 1) { todoListRepository.getTodoListById(todoListId) }
            confirmVerified(todoListRepository)
        }
}