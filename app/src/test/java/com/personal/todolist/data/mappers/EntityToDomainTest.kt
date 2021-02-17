package com.personal.todolist.data.mappers

import com.google.common.truth.Truth
import com.personal.todolist.domain.usecase.UseCaseTest
import com.personal.todolist.utils.createTask
import com.personal.todolist.utils.createTodoList
import com.personal.todolist.utils.createTodoListWithTasks
import org.junit.Test

class EntityToDomainTest : UseCaseTest() {
    private val todoListEntity = createTodoList().toEntity()
    private val taskEntity = createTask().toEntity(todoListEntity.id)
    private val todoListWithTasks = createTodoListWithTasks()

    @Test
    fun `should create task model from task entity`() {
        val task = taskEntity.toDomain()
        task.apply {
            Truth.assertThat(id).isEqualTo(taskEntity.id)
            Truth.assertThat(description).isEqualTo(taskEntity.description)
            Truth.assertThat(complete).isEqualTo(taskEntity.complete)
        }
    }

    @Test
    fun `should create todo list model from todo list with tasks entity`() {
        val todoList = todoListWithTasks.toDomain()
        todoList.apply {
            Truth.assertThat(id).isEqualTo(todoListWithTasks.todoListEntity.id)
            Truth.assertThat(title).isEqualTo(todoListWithTasks.todoListEntity.title)
            Truth.assertThat(tasks).isEqualTo(todoListWithTasks.taskEntities.map { it.toDomain() })
        }
    }
}