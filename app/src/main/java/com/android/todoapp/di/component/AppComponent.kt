package com.android.todoapp.di.component

import android.app.Application
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import com.android.todoapp.data.database.ToDoDatabase
import com.android.todoapp.di.module.DatabaseModule
import com.android.todoapp.di.scope.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@ApplicationScope
@Component(modules = [DatabaseModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun getDatabase(): ToDoDatabase

}