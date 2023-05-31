package com.android.todoapp.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.todoapp.data.model.domain.DomainTask
import java.util.*

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val endDate: Date,
    val status: TaskStatus
)


enum class TaskStatus {
    PENDING,
    COMPLETED,
    NONE
}

fun Task.toDomain(): DomainTask {
    return DomainTask(
        id = this.id,
        title = this.title,
        endDate = this.endDate,
        status = this.status
    )
}

