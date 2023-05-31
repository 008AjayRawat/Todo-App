package com.android.todoapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.todoapp.data.database.dao.TaskDao
import com.android.todoapp.data.model.local.Task

@Database(entities = [Task::class], version = 1)
@TypeConverters(DatabaseTypeConverter::class)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao
}