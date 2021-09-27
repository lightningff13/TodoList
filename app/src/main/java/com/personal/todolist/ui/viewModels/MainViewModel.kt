package com.personal.todolist.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.todolist.common.Resource
import com.personal.todolist.domain.usecase.GetTodoLists
import com.personal.todolist.domain.usecase.UseCase
import com.personal.todolist.ui.TodoListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTodoLists: GetTodoLists
) : ViewModel() {
    private val _todoLists: MutableLiveData<TodoListState> by lazy {
        MutableLiveData<TodoListState>()
    }
    val todoLists: LiveData<TodoListState> = _todoLists

    init {
        getTodoLists()
    }

    private fun getTodoLists() {
        getTodoLists.execute(UseCase.None()).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _todoLists.value = TodoListState(todoLists = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _todoLists.value =
                        TodoListState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _todoLists.value = TodoListState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }
}