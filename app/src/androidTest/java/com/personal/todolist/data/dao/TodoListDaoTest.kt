package com.personal.todolist.data.dao

import com.google.common.truth.Truth.assertThat
import com.personal.todolist.data.dao.utils.createTask
import com.personal.todolist.data.dao.utils.createTodoList
import com.personal.todolist.data.entities.TaskEntity
import com.personal.todolist.data.mappers.toDomain
import com.personal.todolist.data.mappers.toEntity
import com.personal.todolist.domain.models.TodoList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class TodoListDaoTest : DbTest() {
    @Test
    fun should_insert_todoList_entity_to_database() =
        runTest {
            val todoList = createTodoList().toEntity()
            val expectedList = listOf(todoList)

            todoListDao.insert(todoList)
            val todoLists = todoListDao.getAll()
                .first()
                .map {
                      it.todoListEntity
                }
            assertThat(todoLists).isEqualTo(expectedList)
        }

    @Test
    fun should_insert_task_entity_to_database() =
        runTest {
            val todoList = createTodoList()
            val task = createTask()
            val expectedList = mutableListOf<TaskEntity>()


            val todoListId = todoListDao.insert(todoList.toEntity())
            val taskEntity = task.toEntity(todoListId)
            expectedList.add(taskEntity)

            todoListDao.insert(taskEntity)
            val tasks = todoListDao.getAll()
                .first()
                .flatMap { it.taskEntities }

            assertThat(tasks).isEqualTo(expectedList)
        }

    @Test
    fun should_insert_todoList_to_database() =
        runTest {
            val todoList = createTodoList()
            val expectedList = listOf(todoList)

            todoListDao.insert(todoList)
            val todoLists = todoListDao.getAll().first().map { it.toDomain() }

            assertThat(todoLists).isEqualTo(expectedList)
        }

    @Test
    fun should_delete_todoList_entity_from_database() =
        runTest {
            val todoList = createTodoList().toEntity()


            todoListDao.insert(todoList)
            todoListDao.delete(todoList)
            val todoLists = todoListDao.getAll().first().map { it.todoListEntity }

            assertThat(todoLists).isEmpty()
        }

    @Test
    fun should_delete_task_entity_from_database() =
        runTest {
            val todoList = createTodoList()
            val task = createTask()

            val todoListId = todoListDao.insert(todoList.toEntity())
            val taskEntity = task.toEntity(todoListId)

            todoListDao.insert(taskEntity)
            todoListDao.delete(taskEntity)
            val tasks: List<TaskEntity> = todoListDao.getAll().first().flatMap { it.taskEntities }

            assertThat(tasks).isEmpty()
        }

    @Test
    fun should_delete_todoList_from_database() =
        runTest {
            val todoList = createTodoList()

            todoListDao.insert(todoList)
            todoListDao.delete(todoList)
            val todoLists = todoListDao.getAll().first().map { it.toDomain() }

            assertThat(todoLists).isEmpty()
        }

    @Test
    fun should_get_all_todoLists_from_database() =
        runTest {
            val todoList = createTodoList()
            val tasks = listOf(createTask(2))
            val todoList2 = createTodoList(2, tasks = tasks)

            val expectedList = mutableListOf<TodoList>()

            todoListDao.insert(todoList)
            todoListDao.insert(todoList2)
            expectedList.add(todoList)
            expectedList.add(todoList2)

            val todoLists = todoListDao.getAll().first().map { it.toDomain() }

            assertThat(todoLists).isEqualTo(expectedList)
        }

    @Test
    fun should_get_todoList_by_id_from_database() =
        runTest {
            val todoList = createTodoList()
            val tasks = listOf(createTask(2))
            val todoList2 = createTodoList(2, tasks = tasks)

            todoListDao.insert(todoList)
            todoListDao.insert(todoList2)

            val todoListById = todoListDao.getById(todoList.id).first().toDomain()

            assertThat(todoListById).isEqualTo(todoList)
        }

    @Test
    fun should_update_todoList_in_database() =
        runTest {
            val todoList = createTodoList()
            val tasks =
                listOf(createTask(description = "Updated description of the task", complete = true))
            val todoList2 = createTodoList(title = "Updated Title", tasks = tasks)


            todoListDao.insert(todoList)
            todoListDao.insert(todoList2)

            val todoLists = todoListDao.getAll().first().map { it.toDomain() }

            assertThat(todoLists.size).isEqualTo(1)
            assertThat(todoLists.first()).isEqualTo(todoList2)
        }
}