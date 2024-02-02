package com.smith.weatherapp.presentation

import android.app.Application
import com.smith.weatherapp.di.ApplicationComponent
import com.smith.weatherapp.di.DaggerApplicationComponent

class WeatherApp: Application() {
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}