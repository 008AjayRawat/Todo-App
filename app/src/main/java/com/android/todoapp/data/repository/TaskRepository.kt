package com.android.todoapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.android.todoapp.data.database.dao.TaskDao
import com.android.todoapp.data.model.domain.DomainTask
import com.android.todoapp.data.model.domain.toLocal
import com.android.todoapp.data.model.local.toDomain
import javax.inject.Inject

class TaskRepository @Inject constructor(private val dao: TaskDao) : ITaskRepository {

    override suspend fun upsertTask(task: DomainTask) {
        dao.upsertTask(task.toLocal())
    }

    override suspend fun removeTask(task: DomainTask) {
        dao.deleteTask(task.toLocal())
    }

    override fun getAll(): LiveData<List<DomainTask>> {
        return dao.getAll()
            .map { list ->
                list.map { localModel ->
                    localModel.toDomain()
                }
            }
    }


}