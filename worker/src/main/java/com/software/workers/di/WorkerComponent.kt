package com.software.workers.di

import com.software.worker_api.WorkerComponentInterface
import com.software.workers.LoadProductsInListWorker
import com.software.workers.LoadProductsWorker
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [WorkerModule::class],
    dependencies = [WorkerDependencies::class]
)
abstract class WorkerComponent : WorkerComponentInterface {

    companion object {

        @Volatile
        var workerComponent: WorkerComponent? = null
            private set

        @Synchronized
        fun initAndGet(workerDependencies: WorkerDependencies): WorkerComponent? =
            when (workerComponent) {
                null -> {
                    workerComponent = DaggerWorkerComponent.builder()
                        .workerDependencies(workerDependencies)
                        .build()
                    workerComponent
                }

                else -> {
                    workerComponent
                }

            }

        fun get(): WorkerComponent? =
            when (workerComponent) {
                null -> {
                    throw RuntimeException("You must call 'initAndGet(workerDependencies: WorkerDependencies)' method")
                }

                else -> {
                    workerComponent
                }
            }

    }

    abstract fun injectLoadAndSaveProductsWorker(loadAndSaveProductsWorker: LoadProductsWorker)
    abstract fun injectLoadAndSaveProductsInListWorker(loadAndSaveProductsInListWorker: LoadProductsInListWorker)
}
