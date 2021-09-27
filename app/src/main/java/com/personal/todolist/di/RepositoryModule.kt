package com.personal.todolist.di

import com.personal.todolist.data.dao.TodoListDao
import com.personal.todolist.data.repository.TodoListRepositoryImpl
import com.personal.todolist.domain.repository.TodoListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesTodoListRepository(todoListDao: TodoListDao, @IoDispatcher ioDispatcher: CoroutineDispatcher) : TodoListRepository {
        return TodoListRepositoryImpl(todoListDao, ioDispatcher)
    }
}