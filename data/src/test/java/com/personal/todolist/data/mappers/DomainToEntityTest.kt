package com.personal.todolist.data.mappers

import com.google.common.truth.Truth.assertThat
import com.personal.todolist.data.repository.LifecycleTest
import com.personal.todolist.data.repository.createTask
import com.personal.todolist.data.repository.createTodoList
import org.junit.Test

class DomainToEntityTest : LifecycleTest(){
    private val todoList = createTodoList()
    private val task = createTask()

    @Test
    fun `should create todo list entity from todo list model`() {
        val todoListEntity = todoList.toEntity()
        todoListEntity.apply {
            assertThat(id).isEqualTo(todoList.id)
            assertThat(title).isEqualTo(todoList.title)
        }
    }

    @Test
    fun `should create task entity from task model`() {
        val todoListEntity = todoList.toEntity()
        val taskEntity = task.toEntity(todoListEntity.id)
        taskEntity.apply {
            assertThat(id).isEqualTo(task.id)
            assertThat(description).isEqualTo(task.description)
            assertThat(todoListId).isEqualTo(todoListEntity.id)
            assertThat(complete).isEqualTo(task.complete)
        }
    }
}