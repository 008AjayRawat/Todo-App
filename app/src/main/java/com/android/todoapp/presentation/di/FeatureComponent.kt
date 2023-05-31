package com.android.todoapp.presentation.di

import com.android.todoapp.di.component.AppComponent
import com.android.todoapp.di.module.RepositoryModule
import com.android.todoapp.di.module.ViewModelFactoryModule
import com.android.todoapp.di.module.ViewModelModule
import com.android.todoapp.presentation.di.module.DaoModule
import com.android.todoapp.presentation.di.scope.FeatureScope
import com.android.todoapp.presentation.ui.fragment.AddTaskFragment
import com.android.todoapp.presentation.ui.fragment.TaskListFragment
import dagger.Component

@FeatureScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        ViewModelModule::class,
        ViewModelFactoryModule::class,
        RepositoryModule::class,
        DaoModule::class,
    ]
)
interface FeatureComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): FeatureComponent
    }

    fun inject(taskListFragment: TaskListFragment)

    fun inject(addTaskFragment: AddTaskFragment)


}