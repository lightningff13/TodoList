package com.personal.todolist.data.dao

import com.google.common.truth.Truth.assertThat
import com.personal.todolist.data.entities.TaskEntity
import com.personal.todolist.data.entities.TodoListEntity
import com.personal.todolist.data.mappers.toDomain
import com.personal.todolist.data.mappers.toEntity
import com.personal.todolist.domain.models.TodoList
import com.personal.todolist.utils.createTask
import com.personal.todolist.utils.createTodoList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class TodoListDaoTest : DbTest() {
    @Test
    fun should_insert_todoList_entity_to_database() {
        val todoList = createTodoList().toEntity()
        val expectedList = listOf(todoList)
        lateinit var todoLists: List<TodoListEntity>

        runBlocking {
            todoListDao.insert(todoList)
            todoLists = todoListDao.getAll().map { it.todoListEntity }
        }
        assertThat(todoLists).isEqualTo(expectedList)
    }
    
    @Test
    fun should_insert_task_entity_to_database() {
        val todoList = createTodoList()
        val task = createTask()
        val expectedList = mutableListOf<TaskEntity>()
        lateinit var tasks: List<TaskEntity>

        runBlocking {
            val todoListId = todoListDao.insert(todoList.toEntity())
            val taskEntity = task.toEntity(todoListId)
            expectedList.add(taskEntity)

            todoListDao.insert(taskEntity)
            tasks = todoListDao.getAll().flatMap { it.taskEntities }
        }
        assertThat(tasks).isEqualTo(expectedList)
    }

    @Test
    fun should_insert_todoList_to_database() {
        val todoList = createTodoList()
        val expectedList = listOf(todoList)
        lateinit var todoLists: List<TodoList>

        runBlocking {
            todoListDao.insert(todoList)
            todoLists = todoListDao.getAll().map { it.toDomain() }
        }
        assertThat(todoLists).isEqualTo(expectedList)
    }

    @Test
    fun should_delete_todoList_entity_from_database() {
        val todoList = createTodoList().toEntity()
        lateinit var todoLists: List<TodoListEntity>

        runBlocking {
            todoListDao.insert(todoList)
            todoListDao.delete(todoList)
            todoLists = todoListDao.getAll().map { it.todoListEntity }
        }
        assertThat(todoLists).isEmpty()
    }

    @Test
    fun should_delete_task_entity_from_database() {
        val todoList = createTodoList()
        val task = createTask()
        lateinit var tasks: List<TaskEntity>

        runBlocking {
            val todoListId = todoListDao.insert(todoList.toEntity())
            val taskEntity = task.toEntity(todoListId)

            todoListDao.insert(taskEntity)
            todoListDao.delete(taskEntity)
            tasks = todoListDao.getAll().flatMap { it.taskEntities }
        }
        assertThat(tasks).isEmpty()
    }

    @Test
    fun should_delete_todoList_from_database() {
        val todoList = createTodoList()
        lateinit var todoLists: List<TodoList>

        runBlocking {
            todoListDao.insert(todoList)
            todoListDao.delete(todoList)
            todoLists = todoListDao.getAll().map { it.toDomain() }
        }
        assertThat(todoLists).isEmpty()
    }

    @Test
    fun should_get_all_todoLists_from_database() {
        val todoList = createTodoList()
        val tasks = listOf(createTask(2))
        val todoList2 = createTodoList(2, tasks = tasks)

        val expectedList = mutableListOf<TodoList>()
        lateinit var todoLists: List<TodoList>

        runBlocking {
            todoListDao.insert(todoList)
            todoListDao.insert(todoList2)
            expectedList.add(todoList)
            expectedList.add(todoList2)

            todoLists = todoListDao.getAll().map { it.toDomain() }
        }
        assertThat(todoLists).isEqualTo(expectedList)
    }

    @Test
    fun should_get_todoList_by_id_from_database() {
        val todoList = createTodoList()
        val tasks = listOf(createTask(2))
        val todoList2 = createTodoList(2, tasks = tasks)

        lateinit var todoListById: TodoList

        runBlocking {
            todoListDao.insert(todoList)
            todoListDao.insert(todoList2)

            todoListById = todoListDao.getById(todoList.id).toDomain()
        }
        assertThat(todoListById).isEqualTo(todoList)
    }

    @Test
    fun should_update_todoList_in_database() {
        val todoList = createTodoList()
        val tasks = listOf(createTask(description = "Updated description of the task", complete = true))
        val todoList2 = createTodoList(title = "Updated Title", tasks = tasks)

        lateinit var todoLists: List<TodoList>

        runBlocking {
            todoListDao.insert(todoList)
            todoListDao.insert(todoList2)

            todoLists = todoListDao.getAll().map { it.toDomain() }
        }
        assertThat(todoLists.size).isEqualTo(1)
        assertThat(todoLists.first()).isEqualTo(todoList2)
    }
}