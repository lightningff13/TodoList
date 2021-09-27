package com.personal.todolist.di

import android.app.Application
import androidx.room.Room
import com.personal.todolist.data.TodoListDatabase
import com.personal.todolist.data.dao.TodoListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesTodoListDatabase(app: Application): TodoListDatabase {
        return Room.databaseBuilder(
            app,
            TodoListDatabase::class.java,
            TodoListDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesTodoListDao(todoListDatabase: TodoListDatabase): TodoListDao {
        return todoListDatabase.todoListDao()
    }
}