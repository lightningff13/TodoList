package com.personal.todolist.data.dao

import androidx.room.*
import com.personal.todolist.data.entities.TaskEntity
import com.personal.todolist.data.entities.TodoListEntity
import com.personal.todolist.data.entities.TodoListWithTasks
import com.personal.todolist.data.mappers.toEntity
import com.personal.todolist.domain.models.TodoList

@Dao
interface TodoListDao {

    @Transaction
    suspend fun insert(todoList: TodoList): Boolean {
        val todoListId = insert(todoList.toEntity())
        for (task in todoList.tasks) {
            val taskEntity = task.toEntity(todoListId)
            insert(taskEntity)
        }
        return true
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todoListEntity: TodoListEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskEntity: TaskEntity): Long

    @Transaction
    suspend fun delete(todoList: TodoList): Boolean {
        var deletedRowsNumbers = 0
        for (task in todoList.tasks) {
            val taskEntity = task.toEntity(todoList.id)
            deletedRowsNumbers += delete(taskEntity)
        }
        deletedRowsNumbers += delete(todoList.toEntity())
        return deletedRowsNumbers == todoList.tasks.size + 1
    }

    @Delete
    suspend fun delete(todoListEntity: TodoListEntity): Int

    @Delete
    suspend fun delete(taskEntity: TaskEntity): Int

    @Transaction
    @Query("SELECT * FROM todo_list")
    suspend fun getAll(): List<TodoListWithTasks>

    @Transaction
    @Query("SELECT * FROM todo_list where id = :todoListId")
    suspend fun getById(todoListId : Long): TodoListWithTasks
}