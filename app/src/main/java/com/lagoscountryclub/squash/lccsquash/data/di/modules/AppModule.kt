package com.lagoscountryclub.squash.lccsquash.data.di.modules

import com.google.gson.Gson
import com.lagoscountryclub.squash.lccsquash.data.remote.api.ApiSessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideApiSessionManager(gson: Gson): ApiSessionManager = ApiSessionManager(gson)
}
