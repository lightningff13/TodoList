package com.personal.todolist.domain.usecase

import android.database.sqlite.SQLiteException
import com.google.common.truth.Truth
import com.google.common.truth.Truth.*
import com.personal.todolist.common.Resource
import com.personal.todolist.utils.createTodoList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test


class CreateTodoListTest : UseCaseTest(){
    private val createTodoList = CreateTodoList(todoListRepository)

    init {
        coEvery { todoListRepository.addTodoList(any()) } returns true
    }

    @Test
    fun `should add todo list with repository when successful returns a flow with a loading and a success resource`() {
        val todoList = createTodoList()
        runBlocking {
            val elements = createTodoList.execute(todoList).toList()
            assertThat(elements.first()).isInstanceOf(Resource.Loading::class.java)
            assertThat(elements.last()).isInstanceOf(Resource.Success::class.java)
        }
        coVerify(exactly = 1) { todoListRepository.addTodoList(todoList) }
        confirmVerified(todoListRepository)
    }

    @Test
    fun `should add todo list with repository when failing returns a flow with a loading and an error resource`() {
        coEvery { todoListRepository.addTodoList(any()) } throws SQLiteException()
        val todoList = createTodoList()
        runBlocking {
            val elements = createTodoList.execute(todoList).toList()
            assertThat(elements.first()).isInstanceOf(Resource.Loading::class.java)
            assertThat(elements.last()).isInstanceOf(Resource.Error::class.java)
        }
        coVerify(exactly = 1) { todoListRepository.addTodoList(todoList) }
        confirmVerified(todoListRepository)
    }
}