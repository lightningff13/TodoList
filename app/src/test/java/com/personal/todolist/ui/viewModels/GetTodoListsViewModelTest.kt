package com.personal.todolist.ui.viewModels

import com.google.common.truth.Truth.assertThat
import com.personal.todolist.MainDispatcherRule
import com.personal.todolist.common.Resource
import com.personal.todolist.domain.usecase.GetTodoLists
import com.personal.todolist.domain.usecase.UseCase
import com.personal.todolist.ui.TodoListState
import com.personal.todolist.utils.createTodoList
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.BeforeAll

@ExperimentalCoroutinesApi
class GetTodoListsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getTodoListsUseCase: GetTodoLists = mockk()

    @BeforeAll
    fun init() {
        clearMocks(getTodoListsUseCase)
    }

    @Test
    fun `should get list of todo list with use case when successful returns a flow with a loading state and a success state then no loading`() =
        runTest {
            coEvery { getTodoListsUseCase.execute(UseCase.Parameters.None) } returns flow {
                emit(Resource.Loading(isLoading = true))
                emit(Resource.Success(data = listOf(createTodoList())))
                emit(Resource.Loading(isLoading = false))
            }

            val viewModel = GetTodoListsViewModel(getTodoListsUseCase)
            val todoLists = viewModel.todoListsFlow.toList()
            val expectedTodoListStates = listOf(
                TodoListState.Loading(isLoading = true),
                TodoListState.Success(todoLists = listOf(createTodoList())),
                TodoListState.Loading(isLoading = false),
            )

            coVerify(exactly = 1) { getTodoListsUseCase.execute(UseCase.Parameters.None) }
            confirmVerified(getTodoListsUseCase)
            assertThat(todoLists).isEqualTo(expectedTodoListStates)
        }

    @Test
    fun `should get list of todo list with use case when error returns a flow with a loading state and an error state then no loading state`() =
        runTest {
            val errorMessage = "Unexpected error occurred"
            coEvery { getTodoListsUseCase.execute(UseCase.Parameters.None) } returns flow {
                emit(Resource.Loading(isLoading = true))
                emit(Resource.Error(message = errorMessage))
                emit(Resource.Loading(isLoading = false))
            }

            val viewModel = GetTodoListsViewModel(getTodoListsUseCase)
            val todoLists = viewModel.todoListsFlow.toList()
            val expectedTodoListStates = listOf(
                TodoListState.Loading(isLoading = true),
                TodoListState.Error(error = errorMessage),
                TodoListState.Loading(isLoading = false),
            )

            coVerify(exactly = 1) { getTodoListsUseCase.execute(UseCase.Parameters.None) }
            confirmVerified(getTodoListsUseCase)
            assertThat(todoLists).isEqualTo(expectedTodoListStates)
        }
}