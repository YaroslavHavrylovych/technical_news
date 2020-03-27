package com.gmail.yaroslavlancelot.technarium.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.di.worker.ChildWorkerFactory
import com.gmail.yaroslavlancelot.technarium.settings.AppSettings
import java.lang.IllegalArgumentException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

//TODO we must disable  notifications after some period, as it can be annoying (e.g. user is not opening the app for a while)
class NotificationWorker constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val appSettings: AppSettings
) : Worker(context, workerParams) {

    private val newMessageId = 0
    private val messagesChannelId: String = "nr_user_reminder_channel"

    override fun doWork(): Result {
        createMessagesNotificationChannel(applicationContext)
        val builder = NotificationCompat.Builder(applicationContext, messagesChannelId)
        val title = applicationContext.getString(R.string.notification_title)
        val text = applicationContext.getString(R.string.notification_body)
        builder.color = ContextCompat.getColor(applicationContext, R.color.primary)
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        builder.setSmallIcon(R.drawable.ic_notification_icon)
            .setContentTitle(title).setContentText(text)

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(newMessageId, builder.build())
        enqueueWithPeriod(applicationContext, appSettings.notificationPeriod)
        return Result.success()
    }

    private fun createMessagesNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = context.getString(R.string.notification_channel_name)
            val channel = NotificationChannel(messagesChannelId, name, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager: NotificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val tag = "nr_notification_worker_tag"

        fun enqueueWithPeriod(context: Context, period: NotificationPeriod) {
            val workManager = WorkManager.getInstance(context)
            workManager.cancelAllWorkByTag(tag)
            if (period == NotificationPeriod.NONE) return
            val currentDate = Calendar.getInstance()
            val dueDate = Calendar.getInstance()
            dueDate.set(Calendar.HOUR_OF_DAY, period.hour)
            dueDate.set(Calendar.MINUTE, 0)
            dueDate.set(Calendar.SECOND, 0)
            if (dueDate.before(currentDate)) dueDate.add(Calendar.HOUR_OF_DAY, 24)
            val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
            val dailyWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
                .addTag(tag)
                .build()
            workManager.enqueue(dailyWorkRequest)
        }

        enum class NotificationPeriod(val hour: Int, val period: String) {
            MORNING(8, "morning"), LUNCH(12, "lunch"), EVENING(16, "evening"), NIGHT(22, "night"), NONE(-1, "none");

            companion object {
                fun parse(period: String) =
                    when (period) {
                        MORNING.period -> MORNING
                        LUNCH.period -> MORNING
                        EVENING.period -> MORNING
                        NIGHT.period -> MORNING
                        NONE.period -> MORNING
                        else -> throw IllegalArgumentException("Unknown notification period $period")
                    }
            }
        }
    }

    class Factory @Inject constructor(private val settingsProvider: Provider<AppSettings>) : ChildWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters) = NotificationWorker(appContext, params, settingsProvider.get())
    }
}

