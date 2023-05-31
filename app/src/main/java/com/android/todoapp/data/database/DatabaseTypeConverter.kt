package com.android.todoapp.data.database

import androidx.room.TypeConverter
import com.android.todoapp.data.model.local.TaskStatus
import java.util.*

class DatabaseTypeConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? {
        return date?.time
    }

    fun fromTaskStatus(taskStatus: TaskStatus): String {
        return taskStatus.name
    }

    fun toTaskStatus(value: String): TaskStatus {
        return enumValueOf(value)
    }

}
