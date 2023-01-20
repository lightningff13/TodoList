package com.personal.todolist.data.repository

import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.domain.repository.TodoListRepository
import com.personal.todolist.utils.createTodoList
import com.personal.todolist.utils.createTodoListWithTasks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class TodoListRepositoryTest : RepositoryTest() {
    init {
        coEvery { todoListDao.insert(any<TodoList>()) } returns true
        coEvery { todoListDao.delete(any<TodoList>()) } returns true
        coEvery { todoListDao.getById(any()) } returns flowOf(createTodoListWithTasks())
        coEvery { todoListDao.getAll() } returns flowOf(listOf(createTodoListWithTasks()))
    }

    @Test
    fun `should add todo list with dao`() = runTest {
        val todoListRepository: TodoListRepository = TodoListRepositoryImpl(
            todoListDao = todoListDao,
            ioDispatcher = StandardTestDispatcher(testScheduler)
        )

        val todoList = createTodoList()

        todoListRepository.addTodoList(todoList.title, todoList.tasks)

        coVerify(exactly = 1) { todoListDao.insert(todoList) }
        confirmVerified(todoListDao)
    }

    @Test
    fun `should update todo list with dao`() = runTest {
        val todoListRepository: TodoListRepository = TodoListRepositoryImpl(
            todoListDao = todoListDao,
            ioDispatcher = StandardTestDispatcher(testScheduler)
        )

        val todoList = createTodoList()
        todoListRepository.updateTodoList(todoList)

        coVerify(exactly = 1) { todoListDao.insert(todoList) }
        confirmVerified(todoListDao)
    }

    @Test
    fun `should delete todo list with dao`() = runTest {
        val todoListRepository: TodoListRepository = TodoListRepositoryImpl(
            todoListDao = todoListDao,
            ioDispatcher = StandardTestDispatcher(testScheduler)
        )

        val todoList = createTodoList()
        todoListRepository.deleteTodoList(todoList)

        coVerify(exactly = 1) { todoListDao.delete(todoList) }
        confirmVerified(todoListDao)
    }

    @Test
    fun `should get todo list by id with dao`() = runTest {
        val todoListRepository: TodoListRepository = TodoListRepositoryImpl(
            todoListDao = todoListDao,
            ioDispatcher = StandardTestDispatcher(testScheduler)
        )
        val todoList = createTodoList()

        todoListRepository.getTodoListById(todoList.id)

        coVerify(exactly = 1) { todoListDao.getById(todoList.id) }
        confirmVerified(todoListDao)
    }

    @Test
    fun `should get list of todo list with dao`() = runTest {
        val todoListRepository: TodoListRepository = TodoListRepositoryImpl(
            todoListDao = todoListDao,
            ioDispatcher = StandardTestDispatcher(testScheduler)
        )
        todoListRepository.getTodoLists()

        coVerify(exactly = 1) { todoListDao.getAll() }
        confirmVerified(todoListDao)
    }
}