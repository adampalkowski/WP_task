package com.example.wp_task.di

import com.example.wp_task.MainActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules=[AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}