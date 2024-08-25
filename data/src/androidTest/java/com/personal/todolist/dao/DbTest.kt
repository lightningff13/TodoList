package com.personal.todolist.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.personal.todolist.data.dao.TodoListDao
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.Executors


@RunWith(AndroidJUnit4::class)
abstract class DbTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: com.personal.todolist.data.TodoListDatabase
    lateinit var todoListDao: TodoListDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            com.personal.todolist.data.TodoListDatabase::class.java
        ).setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
        todoListDao = db.todoListDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}