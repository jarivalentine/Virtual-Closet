package be.howest.jarivalentine.virtualcloset.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class LaundryReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        makeLaundryReminderNotification(
            applicationContext
        )

        return Result.success()
    }
}