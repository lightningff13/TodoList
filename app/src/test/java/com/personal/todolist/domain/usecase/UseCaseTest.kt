package com.personal.todolist.domain.usecase

import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Before

abstract class UseCaseTest {
    protected lateinit var todoList: TodoList
    protected lateinit var todoListRepository: TodoListRepository

    @Before
    open fun setUp() {
        todoListRepository = mockk()
        todoList = mockkClass(TodoList::class)
        every { todoList.id } returns 1
    }
}