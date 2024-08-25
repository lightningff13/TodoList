package com.personal.todolist.data.dao

import android.database.sqlite.SQLiteException
import androidx.room.*
import com.personal.todolist.common.models.TodoList
import com.personal.todolist.data.entities.TaskEntity
import com.personal.todolist.data.entities.TodoListEntity
import com.personal.todolist.data.entities.TodoListWithTasks
import com.personal.todolist.data.mappers.toEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {

    @Transaction
    suspend fun insert(todoList: TodoList): Long {
        val todoListId = insert(todoList.toEntity())
        for (task in todoList.tasks) {
            val taskEntity = task.toEntity(todoListId)
            insert(taskEntity)
        }
        return todoListId
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Throws(SQLiteException::class)
    suspend fun insert(todoListEntity: TodoListEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Throws(SQLiteException::class)
    suspend fun insert(taskEntity: TaskEntity): Long

    @Query("UPDATE todo_list SET title = :title WHERE id = :todoListId")
    suspend fun updateTodoList(todoListId: Long, title: String): Int

    @Query("UPDATE task SET description = :description WHERE id = :taskId")
    suspend fun updateTaskDescription(taskId: Long, description: String): Int

    @Query("UPDATE task SET complete = :complete WHERE id = :taskId")
    suspend fun updateTaskCompletion(taskId: Long, complete: Boolean): Int

    @Transaction
    suspend fun delete(todoList: TodoList): Boolean {
        var deletedRowsNumbers = 0
        for (task in todoList.tasks) {
            deletedRowsNumbers += deleteTask(task.id)
        }
        deletedRowsNumbers += delete(todoList.toEntity())
        return deletedRowsNumbers == todoList.tasks.size + 1
    }

    @Delete
    @Throws(SQLiteException::class)
    suspend fun delete(todoListEntity: TodoListEntity): Int

    @Query("DELETE FROM task where id = :taskId")
    suspend fun deleteTask(taskId: Long): Int

    @Transaction
    @Query("SELECT * FROM todo_list")
    @Throws(SQLiteException::class)
    fun getAll(): Flow<List<TodoListWithTasks>>

    @Transaction
    @Query("SELECT * FROM todo_list where id = :todoListId")
    @Throws(SQLiteException::class)
    fun getById(todoListId : Long): Flow<TodoListWithTasks>
}