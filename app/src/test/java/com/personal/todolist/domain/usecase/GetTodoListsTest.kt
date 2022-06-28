package com.personal.todolist.domain.usecase

import android.database.sqlite.SQLiteException
import com.google.common.truth.Truth
import com.google.common.truth.Truth.*
import com.personal.todolist.common.Resource
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.usecase.UseCase.Parameters
import com.personal.todolist.utils.createTodoList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GetTodoListsTest : UseCaseTest() {
    private val getTodoLists = GetTodoLists(todoListRepository)

    init {
        coEvery { todoListRepository.getTodoLists() } returns listOf(createTodoList())
    }

    @Test
    fun `should get list of todo list with repository when successful returns a flow with a loading and a success resource then no loading`() =
        runTest {
            val elements = getTodoLists.execute(params = Parameters.None).toList()
            assertThat(elements.first()).apply {
                isInstanceOf(Resource.Loading::class.java)
                isEqualTo(Resource.Loading<Resource<List<TodoList>>>(true))
            }
            assertThat(elements[1]).isInstanceOf(Resource.Success::class.java)
            assertThat(elements.last()).apply {
                isInstanceOf(Resource.Loading::class.java)
                isEqualTo(Resource.Loading<Resource<List<TodoList>>>(false))
            }
            coVerify(exactly = 1) { todoListRepository.getTodoLists() }
            confirmVerified(todoListRepository)
        }

    @Test
    fun `should get list of todo list with repository when failing returns a flow with a loading and an error resource then no loading`() =
        runTest {
            coEvery { todoListRepository.getTodoLists() } throws SQLiteException()

            val elements = getTodoLists.execute(params = Parameters.None).toList()
            assertThat(elements.first()).apply {
                isInstanceOf(Resource.Loading::class.java)
                isEqualTo(Resource.Loading<Resource<List<TodoList>>>(true))
            }
            assertThat(elements[1]).isInstanceOf(Resource.Error::class.java)
            assertThat(elements.last()).apply {
                isInstanceOf(Resource.Loading::class.java)
                isEqualTo(Resource.Loading<Resource<List<TodoList>>>(false))
            }

            coVerify(exactly = 1) { todoListRepository.getTodoLists() }
            confirmVerified(todoListRepository)
        }
}