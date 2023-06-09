package be.howest.jarivalentine.virtualcloset

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import be.howest.jarivalentine.virtualcloset.data.AppContainer
import be.howest.jarivalentine.virtualcloset.data.AppDataContainer
import be.howest.jarivalentine.virtualcloset.worker.LaundryReminderWorker
import java.io.Console
import java.util.concurrent.TimeUnit

class VirtualClosetApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        scheduleUnavailableItemsWorker()
    }

    private fun scheduleUnavailableItemsWorker() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val isWorkerScheduled: Boolean = sharedPreferences.getBoolean("worker_scheduled", false)

        if (!isWorkerScheduled) {
            val workRequest = PeriodicWorkRequestBuilder<LaundryReminderWorker>(
                1,
                TimeUnit.DAYS
            ).build()

            WorkManager.getInstance(this).enqueue(workRequest)

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("worker_scheduled", true)
            editor.apply()
        }
    }
}
