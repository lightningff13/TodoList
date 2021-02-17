package com.personal.todolist.data.repository

import com.personal.todolist.data.dao.TodoListDao
import io.mockk.clearMocks
import io.mockk.mockk
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class RepositoryTest {
    protected val todoListDao: TodoListDao = mockk()

    @BeforeAll
    fun init(){
        clearMocks(todoListDao)
    }
}