package com.android.todoapp.data.model.domain

import com.android.todoapp.data.model.local.Task
import com.android.todoapp.data.model.local.TaskStatus
import java.util.*

data class DomainTask(
    var id: Int = 0,
    var title: String,
    var endDate: Date,
    var status: TaskStatus
)


fun DomainTask.toLocal(): Task {
    return Task(
        id = this.id,
        title = this.title,
        endDate = this.endDate,
        status = this.status
    )
}

