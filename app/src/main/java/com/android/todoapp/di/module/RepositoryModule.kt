package com.android.todoapp.di.module

import com.android.todoapp.data.database.dao.TaskDao
import com.android.todoapp.data.repository.ITaskRepository
import com.android.todoapp.data.repository.TaskRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesTaskRepository(dao: TaskDao): ITaskRepository {
        return TaskRepository(dao)
    }

}