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
class DeleteTodoListTest : UseCaseTest() {
    private val deleteTodoList = DeleteTodoList((todoListRepository))

    init {
        coEvery { todoListRepository.deleteTodoList(any()) } returns true
    }

    @Test
    fun `should delete todo list`() =
        runTest {
            val todoList = createTodoList()

            val elements = deleteTodoList.execute(todoList).toList()
            Truth.assertThat(elements.last()).isEqualTo(true)

            coVerify(exactly = 1) { todoListRepository.deleteTodoList(todoList) }
            confirmVerified(todoListRepository)
        }
}