package com.android.todoapp.presentation.di.module

import com.android.todoapp.data.database.ToDoDatabase
import com.android.todoapp.data.database.dao.TaskDao
import com.android.todoapp.presentation.di.scope.FeatureScope
import dagger.Module
import dagger.Provides

@Module
class DaoModule {

    @Provides
    fun providesTaskDao(database: ToDoDatabase): TaskDao {
        return database.getTaskDao()
    }
}