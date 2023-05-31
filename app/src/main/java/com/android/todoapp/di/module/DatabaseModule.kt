package com.android.todoapp.di.module

import android.app.Application
import androidx.room.Room
import com.android.todoapp.data.database.ToDoDatabase
import com.android.todoapp.data.database.dao.TaskDao
import com.android.todoapp.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @ApplicationScope
    @Provides
    fun provideTodoRoomDatabase(application: Application): ToDoDatabase {
        return Room.databaseBuilder(
            application,
            ToDoDatabase::class.java,
            "todo-db"
        ).build()
    }

}