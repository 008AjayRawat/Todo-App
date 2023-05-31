package com.android.todoapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.todoapp.data.model.domain.DomainTask
import com.android.todoapp.data.repository.ITaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    private val repository: ITaskRepository
) : ViewModel() {

    //Observer this live data for task list
    val taskListLiveData: LiveData<List<DomainTask>> get() = repository.getAll()

    fun upsertTask(task: DomainTask) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.upsertTask(task)
        }
    }

    fun removeTask(task: DomainTask) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeTask(task)
        }
    }

}