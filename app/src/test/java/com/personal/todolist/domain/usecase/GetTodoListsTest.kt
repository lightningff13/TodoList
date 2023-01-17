package com.personal.todolist.domain.usecase

import android.database.sqlite.SQLiteException
import com.google.common.truth.Truth.assertThat
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
class GetTodoListsTest : UseCaseTest() {
    private val getTodoLists = GetTodoLists(todoListRepository)

    init {
        coEvery { todoListRepository.getTodoLists() } returns flowOf(listOf (createTodoList()))
    }

    @Test
    fun `should returns a flow of list of todo list`() =
        runTest {
            val elements = getTodoLists.execute().toList()
            assertThat(elements.last()).isEqualTo(listOf(createTodoList()))
            coVerify(exactly = 1) { todoListRepository.getTodoLists() }
            confirmVerified(todoListRepository)
        }
}