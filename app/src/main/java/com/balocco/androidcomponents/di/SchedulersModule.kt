package com.balocco.androidcomponents.di

import com.balocco.androidcomponents.common.scheduler.RealSchedulerProvider
import com.balocco.androidcomponents.common.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides

/* Module that contains dependencies for rx schedulers. */
@Module
class SchedulersModule {

    @Provides
    @ApplicationScope
    fun provideSchedulerProvider(
    ): SchedulerProvider = RealSchedulerProvider()
}