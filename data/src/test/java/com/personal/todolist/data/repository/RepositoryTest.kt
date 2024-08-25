package com.personal.todolist.data.repository

import com.personal.todolist.data.dao.TodoListDao
import io.mockk.clearMocks
import io.mockk.mockk
import org.junit.jupiter.api.BeforeAll

abstract class RepositoryTest : LifecycleTest(){
    protected val todoListDao: TodoListDao = mockk()

    @BeforeAll
    fun init(){
        clearMocks(todoListDao)
    }
}