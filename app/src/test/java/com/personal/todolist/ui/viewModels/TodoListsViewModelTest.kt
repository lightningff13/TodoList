package com.personal.todolist.ui.viewModels

import com.google.common.truth.Truth.assertThat
import com.personal.todolist.MainDispatcherRule
import com.personal.todolist.data.repository.FakeTodoListRepository
import com.personal.todolist.domain.usecase.CreateTodoListUseCase
import com.personal.todolist.domain.usecase.DeleteTodoListUseCase
import com.personal.todolist.domain.usecase.GetTodoListsUseCase
import com.personal.todolist.utils.createTodoList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TodoListsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeTodoListRepository = FakeTodoListRepository()
    private val getTodoListsUseCase = GetTodoListsUseCase(fakeTodoListRepository)
    private val deleteTodoListUseCase = DeleteTodoListUseCase(fakeTodoListRepository)
    private val createTodoListUseCase = CreateTodoListUseCase(fakeTodoListRepository)

    private lateinit var viewModel: TodoListsViewModel

    @Before
    fun setup() {
        viewModel =
            TodoListsViewModel(getTodoListsUseCase, createTodoListUseCase, deleteTodoListUseCase)
    }

    @Test
    fun `should get list of todo list with use case when successful returns a flow with a loading state and a success state`() =
        runTest {
            val collectJob = launch(UnconfinedTestDispatcher()) {
                viewModel.todoListUiState.collect()
            }

            assertThat(viewModel.todoListUiState.value).isEqualTo(TodoListState.Loading)

            fakeTodoListRepository.sendTodoLists(listOf(createTodoList()))
            assertThat(viewModel.todoListUiState.value).isEqualTo(
                TodoListState.Success(
                    todoLists = listOf(
                        createTodoList()
                    )
                )
            )

            collectJob.cancel()
        }
}