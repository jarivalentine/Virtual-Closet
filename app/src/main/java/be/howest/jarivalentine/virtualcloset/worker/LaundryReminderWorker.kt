package be.howest.jarivalentine.virtualcloset.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import be.howest.jarivalentine.virtualcloset.VirtualClosetApplication

class LaundryReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val application = applicationContext as VirtualClosetApplication
        val itemRepository = application.container.itemRepository

        val hasUnavailableItems = itemRepository.hasUnavailableItems()

        if (hasUnavailableItems) {
            makeLaundryReminderNotification(applicationContext)
        }

        return Result.success()
    }
}