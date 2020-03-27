package com.gmail.yaroslavlancelot.technarium.notification

import com.gmail.yaroslavlancelot.technarium.di.worker.ChildWorkerFactory
import com.gmail.yaroslavlancelot.technarium.di.worker.WorkerKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NotificationModule {
    @Binds
    @IntoMap
    @WorkerKey(NotificationWorker::class)
    fun bindHelloWorldWorker(factory: NotificationWorker.Factory): ChildWorkerFactory
}