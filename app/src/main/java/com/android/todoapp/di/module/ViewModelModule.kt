package com.android.todoapp.di.module

import androidx.lifecycle.ViewModel
import com.android.todoapp.di.ViewModelKey
import com.android.todoapp.presentation.viewmodel.TaskViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @IntoMap
    @Binds
    @ViewModelKey(TaskViewModel::class)
    abstract fun bindsTaskViewModel(viewModel: TaskViewModel): ViewModel
}