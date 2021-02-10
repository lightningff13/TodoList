package com.personal.todolist.domain.usecase

import com.personal.todolist.data.entities.TodoListWithTasks
import com.personal.todolist.domain.repository.TodoListRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Before

abstract class UseCaseTest {
    protected lateinit var todoListWithTasks: TodoListWithTasks
    protected lateinit var todoListRepository: TodoListRepository

    @Before
    open fun setUp() {
        todoListRepository = mockk()
        todoListWithTasks = mockkClass(TodoListWithTasks::class)
        every { todoListWithTasks.todoList.id } returns 1
    }
}