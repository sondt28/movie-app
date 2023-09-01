package com.son.movie

import android.app.Application
import timber.log.Timber

class MovieApplication : Application() {
//    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
//        delayedInit()
    }

//    private fun delayedInit() {
//        applicationScope.launch {
//            setupRecurringWork()
//        }
//    }
//
//    private fun setupRecurringWork() {
//        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.METERED)
//            .setRequiresBatteryNotLow(true).setRequiresCharging(true).apply {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    setRequiresDeviceIdle(true)
//                }
//            }.build()
//
//        val repeatingRequest =
//            PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
//                .setConstraints(constraints)
//                .build()
//
//        WorkManager.getInstance().enqueueUniquePeriodicWork(
//            RefreshDataWorker.WORK_NAME,
//            ExistingPeriodicWorkPolicy.KEEP,
//            repeatingRequest
//        )
//    }
}