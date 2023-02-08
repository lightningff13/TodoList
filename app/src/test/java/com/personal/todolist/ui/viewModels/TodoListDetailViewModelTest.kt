package com.personal.todolist.ui.viewModels

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import com.personal.todolist.MainDispatcherRule
import com.personal.todolist.data.repository.FakeTodoListRepository
import com.personal.todolist.domain.usecase.AddTaskToTodoListUseCase
import com.personal.todolist.domain.usecase.DeleteTaskUseCase
import com.personal.todolist.domain.usecase.GetTodoListByIdUseCase
import com.personal.todolist.domain.usecase.UpdateTaskCompletionUseCase
import com.personal.todolist.domain.usecase.UpdateTaskDescriptionUseCase
import com.personal.todolist.domain.usecase.UpdateTodoListUseCase
import com.personal.todolist.ui.navigation.destinations.TodoListDetailNavigationDestination
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
class TodoListDetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeTodoListRepository = FakeTodoListRepository()
    private val savedStateHandle = SavedStateHandle().apply { set(TodoListDetailNavigationDestination.todoListIdArg, 1) }
    private val getTodoListByIdUseCase = GetTodoListByIdUseCase(fakeTodoListRepository)
    private val updateTodoListUseCase = UpdateTodoListUseCase(fakeTodoListRepository)
    private val addTaskToTodoListUseCase = AddTaskToTodoListUseCase(fakeTodoListRepository)
    private val updateTaskDescriptionUseCase = UpdateTaskDescriptionUseCase(fakeTodoListRepository)
    private val updateTaskCompletionUseCase = UpdateTaskCompletionUseCase(fakeTodoListRepository)
    private val deleteTaskUseCase = DeleteTaskUseCase(fakeTodoListRepository)

    private lateinit var viewModel: TodoListDetailViewModel

    @Before
    fun setup() {
        viewModel =
            TodoListDetailViewModel(
                savedStateHandle,
                getTodoListByIdUseCase,
                updateTodoListUseCase,
                addTaskToTodoListUseCase,
                updateTaskDescriptionUseCase,
                updateTaskCompletionUseCase,
                deleteTaskUseCase
            )
    }

    @Test
    fun `should get todo list by id with use case when successful returns a flow with a loading state and a success state`() =
        runTest {
            val collectJob = launch(UnconfinedTestDispatcher()) {
                viewModel.todoListDetailUiState.collect()
            }

            Truth.assertThat(viewModel.todoListDetailUiState.value).isEqualTo(TodoListDetailState.Loading)

            val todoList = createTodoList()
            fakeTodoListRepository.addTodoList(todoListTitle = todoList.title, tasks = todoList.tasks)
            Truth.assertThat(viewModel.todoListDetailUiState.value).isEqualTo(
                TodoListDetailState.Success(
                    todoList = todoList
                )
            )

            collectJob.cancel()
        }
}