package com.android.todoapp

import android.app.Application
import com.android.todoapp.di.component.AppComponent
import com.android.todoapp.di.component.DaggerAppComponent

class ToDoApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

}