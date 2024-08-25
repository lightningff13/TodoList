package com.personal.todolist.domain.mappers

import com.google.common.truth.Truth
import com.personal.todolist.data.mappers.toDomain
import com.personal.todolist.data.mappers.toEntity
import com.personal.todolist.data.repository.createTask
import com.personal.todolist.data.repository.createTodoList
import com.personal.todolist.data.repository.createTodoListWithTasks
import com.personal.todolist.domain.usecase.UseCaseTest
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