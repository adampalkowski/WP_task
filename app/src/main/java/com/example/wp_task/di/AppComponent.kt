package com.example.wp_task.di

import com.example.wp_task.MainActivity
import com.example.wp_task.viewModels.FavouritesViewModel
import dagger.Component
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@Component(modules=[AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}