package kg.tutorial.weather_app56

import android.app.Application
import kg.tutorial.weather_app56.di.dataModule
import kg.tutorial.weather_app56.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(vmModule, dataModule))
        }
    }
}