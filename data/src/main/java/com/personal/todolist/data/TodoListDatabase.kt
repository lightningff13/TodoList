package com.personal.todolist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.personal.todolist.data.dao.TodoListDao
import com.personal.todolist.data.entities.TaskEntity
import com.personal.todolist.data.entities.TodoListEntity

@Database(
    entities = [TodoListEntity::class, TaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TodoListDatabase : RoomDatabase() {
    abstract fun todoListDao(): TodoListDao

    companion object {
        const val DATABASE_NAME = "todolist_db"
    }
}