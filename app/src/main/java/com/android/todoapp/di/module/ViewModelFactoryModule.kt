package com.android.todoapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.android.todoapp.presentation.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}