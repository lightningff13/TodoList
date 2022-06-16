package com.personal.todolist.data.repository

import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import com.personal.todolist.utils.createTodoList
import com.personal.todolist.utils.createTodoListWithTasks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Test

@ExperimentalCoroutinesApi
class TodoListRepositoryTest : RepositoryTest(){
    private val testDispatcher = UnconfinedTestDispatcher()
    private val todoListRepository: TodoListRepository = TodoListRepositoryImpl(todoListDao, testDispatcher)

    init {
        coEvery { todoListDao.insert(any<TodoList>()) } returns true
        coEvery { todoListDao.delete(any<TodoList>()) } returns true
        coEvery { todoListDao.getById(any()) } returns createTodoListWithTasks()
        coEvery { todoListDao.getAll() } returns listOf(createTodoListWithTasks())
    }

    @Test
    fun `should add todo list with dao`() {
        val todoList = createTodoList()
        runBlocking {
            todoListRepository.addTodoList(todoList)
        }
        coVerify(exactly = 1) { todoListDao.insert(todoList) }
        confirmVerified(todoListDao)
    }

    @Test
    fun `should update todo list with dao`() {
        val todoList = createTodoList()
        runBlocking {
            todoListRepository.updateTodoList(todoList)
        }
        coVerify(exactly = 1) { todoListDao.insert(todoList) }
        confirmVerified(todoListDao)
    }

    @Test
    fun `should delete todo list with dao`() {
        val todoList = createTodoList()
        runBlocking {
            todoListRepository.deleteTodoList(todoList)
        }
        coVerify(exactly = 1) { todoListDao.delete(todoList) }
        confirmVerified(todoListDao)
    }

    @Test
    fun `should get todo list by id with dao`() {
        val todoList = createTodoList()
        runBlocking {
            todoListRepository.getTodoListById(todoList.id)
        }
        coVerify(exactly = 1) { todoListDao.getById(todoList.id) }
        confirmVerified(todoListDao)
    }

    @Test
    fun `should get list of todo list with dao`() {
        runBlocking {
            todoListRepository.getTodoLists()
        }
        coVerify(exactly = 1) { todoListDao.getAll() }
        confirmVerified(todoListDao)
    }
}