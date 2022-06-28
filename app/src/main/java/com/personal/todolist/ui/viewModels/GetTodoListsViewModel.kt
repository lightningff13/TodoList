package com.personal.todolist.ui.viewModels

import androidx.lifecycle.ViewModel
import com.personal.todolist.common.Resource
import com.personal.todolist.domain.usecase.GetTodoLists
import com.personal.todolist.domain.usecase.UseCase.Parameters
import com.personal.todolist.ui.TodoListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class GetTodoListsViewModel @Inject constructor(
    getTodoLists: GetTodoLists,
) : ViewModel() {

    val todoListsFlow = getTodoLists
        .execute(Parameters.None)
        .map { result ->
            when (result) {
                is Resource.Success -> {
                    TodoListState.Success(todoLists = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    TodoListState.Error(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    TodoListState.Loading(isLoading = result.isLoading)
                }
            }
        }
}
