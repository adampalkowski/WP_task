package com.example.wp_task

import android.app.Application
import com.example.wp_task.di.AppComponent
import com.example.wp_task.di.AppModule
import com.example.wp_task.di.DaggerAppComponent

class MyApp:Application() {
     lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent=DaggerAppComponent.builder().appModule(AppModule(this))
            .build()
    }
}