package com.personal.todolist.domain.usecase

import android.database.sqlite.SQLiteException
import com.google.common.truth.Truth.assertThat
import com.personal.todolist.common.Resource
import com.personal.todolist.utils.createTodoList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test


@ExperimentalCoroutinesApi
class CreateTodoListTest : UseCaseTest() {
    private val createTodoList = CreateTodoList(todoListRepository)

    init {
        coEvery { todoListRepository.addTodoList(any()) } returns true
    }

    @Test
    fun `should add todo list`() =
        runTest {
            val todoList = createTodoList()

            val elements = createTodoList.execute(todoList).toList()
            assertThat(elements.last()).isEqualTo(true)

            coVerify(exactly = 1) { todoListRepository.addTodoList(todoList) }
            confirmVerified(todoListRepository)
        }
}