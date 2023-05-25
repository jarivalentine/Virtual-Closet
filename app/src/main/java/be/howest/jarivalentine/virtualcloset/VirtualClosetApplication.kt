package be.howest.jarivalentine.virtualcloset

import android.app.Application
import be.howest.jarivalentine.virtualcloset.data.AppContainer
import be.howest.jarivalentine.virtualcloset.data.AppDataContainer

class VirtualClosetApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
