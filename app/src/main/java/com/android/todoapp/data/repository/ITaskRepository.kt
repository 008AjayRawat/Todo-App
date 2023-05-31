package com.android.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.android.todoapp.data.model.domain.DomainTask

interface ITaskRepository {

    suspend fun upsertTask(task: DomainTask)

    suspend fun removeTask(task: DomainTask)

    fun getAll(): LiveData<List<DomainTask>>
}