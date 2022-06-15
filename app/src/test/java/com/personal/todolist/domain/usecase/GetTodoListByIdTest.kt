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

class GetTodoListByIdTest : UseCaseTest(){
    private val getTodoListById = GetTodoListById(todoListRepository)

    init {
        coEvery { todoListRepository.getTodoListById(any()) } returns createTodoList()
    }

    @Test
    fun `should update todo list with repository when successful returns a flow with a loading and a success resource`() {
        val todoListId = createTodoList().id
        runBlocking {
            val elements = getTodoListById.execute(todoListId).toList()
            Truth.assertThat(elements.first()).isInstanceOf(Resource.Loading::class.java)
            Truth.assertThat(elements.last()).isInstanceOf(Resource.Success::class.java)
        }
        coVerify(exactly = 1) { todoListRepository.getTodoListById(todoListId) }
        confirmVerified(todoListRepository)
    }

    @Test
    fun `should update todo list with repository when failing returns a flow with a loading and an error resource`() {
        coEvery { todoListRepository.getTodoListById(any()) } throws SQLiteException()
        val todoListId = createTodoList().id
        runBlocking {
            val elements = getTodoListById.execute(todoListId).toList()
            Truth.assertThat(elements.first()).isInstanceOf(Resource.Loading::class.java)
            Truth.assertThat(elements.last()).isInstanceOf(Resource.Error::class.java)
        }
        coVerify(exactly = 1) { todoListRepository.getTodoListById(todoListId) }
        confirmVerified(todoListRepository)
    }
}