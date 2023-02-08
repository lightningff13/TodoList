package com.personal.todolist.ui.viewModels

sealed class TodoListCreatedEvent {
    object Initial : TodoListCreatedEvent()
    data class Success(val todoListId: Long) : TodoListCreatedEvent()
    data class Error(val error: String) : TodoListCreatedEvent()
}