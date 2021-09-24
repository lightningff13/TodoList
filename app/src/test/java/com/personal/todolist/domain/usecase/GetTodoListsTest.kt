package com.personal.todolist.domain.usecase

import android.database.sqlite.SQLiteException
import com.google.common.truth.Truth
import com.personal.todolist.common.Resource
import com.personal.todolist.utils.createTodoList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetTodoListsTest : UseCaseTest() {
    private val getTodoLists = GetTodoLists(todoListRepository)

    init {
        coEvery { todoListRepository.getTodoLists() } returns listOf(createTodoList())
    }

    @Test
    fun `should get list of todo list with repository when successful returns a flow with a loading and a success resource`() {
        runBlocking {
            val elements = getTodoLists.execute(UseCase.None()).toList()
            Truth.assertThat(elements.first()).isInstanceOf(Resource.Loading::class.java)
            Truth.assertThat(elements.last()).isInstanceOf(Resource.Success::class.java)
        }
        coVerify(exactly = 1) { todoListRepository.getTodoLists() }
        confirmVerified(todoListRepository)
    }

    @Test
    fun `should get list of todo list with repository when failing returns a flow with a loading and an error resource`() {
        coEvery { todoListRepository.getTodoLists() } throws SQLiteException()
        runBlocking {
            val elements = getTodoLists.execute(UseCase.None()).toList()
            Truth.assertThat(elements.first()).isInstanceOf(Resource.Loading::class.java)
            Truth.assertThat(elements.last()).isInstanceOf(Resource.Error::class.java)
        }
        coVerify(exactly = 1) { todoListRepository.getTodoLists() }
        confirmVerified(todoListRepository)
    }
}