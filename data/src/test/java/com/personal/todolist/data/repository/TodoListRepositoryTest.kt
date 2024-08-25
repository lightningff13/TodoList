package com.personal.todolist.data.repository

import com.personal.todolist.common.models.TodoList
import com.personal.todolist.data.entities.TaskEntity
import com.personal.todolist.data.mappers.toEntity
import com.personal.todolist.domain.repository.TodoListRepository
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
        coEvery { todoListDao.insert(any<TodoList>()) } returns 1L
        coEvery { todoListDao.insert(any<TaskEntity>()) } returns 1L
        coEvery { todoListDao.updateTodoList(any(), any()) } returns 1
        coEvery { todoListDao.updateTaskDescription(any(), any()) } returns 1
        coEvery { todoListDao.updateTaskCompletion(any(), any()) } returns 1
        coEvery { todoListDao.delete(any<TodoList>()) } returns true
        coEvery { todoListDao.deleteTask(any()) } returns 1
        coEvery { todoListDao.getById(any()) } returns flowOf(createTodoListWithTasks())
        coEvery { todoListDao.getAll() } returns flowOf(listOf(createTodoListWithTasks()))
    }

    @Test
    fun `should add todo list with dao`() = runTest {
        val todoListRepository: TodoListRepository = TodoListRepositoryImpl(
            todoListDao = todoListDao,
            ioDispatcher = StandardTestDispatcher(testScheduler)
        )

        val todoList = createTodoList(id = 0)

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

        val newTitle = "newTitle"
        val todoList = createTodoList()
        todoListRepository.updateTodoList(todoList.id, newTitle)

        coVerify(exactly = 1) { todoListDao.updateTodoList(todoList.id, newTitle) }
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

    @Test
    fun `should add task to todo list with dao`() = runTest {
        val todoListRepository: TodoListRepository = TodoListRepositoryImpl(
            todoListDao = todoListDao,
            ioDispatcher = StandardTestDispatcher(testScheduler)
        )
        val todoList = createTodoList()
        val task = createTask(id = 0)

        todoListRepository.addTaskToTodoList(
            todoListId = todoList.id,
            taskDescription = task.description
        )

        coVerify(exactly = 1) { todoListDao.insert(taskEntity = task.toEntity(todoList.id)) }
        confirmVerified(todoListDao)
    }

    @Test
    fun `should update task description with dao`() = runTest {
        val todoListRepository: TodoListRepository = TodoListRepositoryImpl(
            todoListDao = todoListDao,
            ioDispatcher = StandardTestDispatcher(testScheduler)
        )
        val task = createTask()
        val newDescription = "newDescription"

        todoListRepository.updateTaskDescription(
            taskId = task.id,
            description = newDescription
        )

        coVerify(exactly = 1) {
            todoListDao.updateTaskDescription(
                taskId = task.id,
                description = newDescription
            )
        }
        confirmVerified(todoListDao)
    }

    @Test
    fun `should update task completion with dao`() = runTest {
        val todoListRepository: TodoListRepository = TodoListRepositoryImpl(
            todoListDao = todoListDao,
            ioDispatcher = StandardTestDispatcher(testScheduler)
        )
        val task = createTask()
        val newComplete = true

        todoListRepository.updateTaskCompletion(
            taskId = task.id,
            complete = newComplete
        )

        coVerify(exactly = 1) {
            todoListDao.updateTaskCompletion(
                taskId = task.id,
                complete = newComplete
            )
        }
        confirmVerified(todoListDao)
    }

    @Test
    fun `should delete task with dao`() = runTest {
        val todoListRepository: TodoListRepository = TodoListRepositoryImpl(
            todoListDao = todoListDao,
            ioDispatcher = StandardTestDispatcher(testScheduler)
        )
        val task = createTask()

        todoListRepository.deleteTask(
            taskId = task.id
        )

        coVerify(exactly = 1) { todoListDao.deleteTask(taskId = task.id) }
        confirmVerified(todoListDao)
    }
}