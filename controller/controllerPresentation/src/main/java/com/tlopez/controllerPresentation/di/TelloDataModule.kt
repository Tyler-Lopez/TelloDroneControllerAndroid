package com.tlopez.controllerPresentation.di

import android.content.Context
import com.tlopez.controllerPresentation.FileUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TelloPresentationModule {
    @Provides
    @Singleton
    fun provideFileUtils(
        @ApplicationContext context: Context
    ) : FileUtils = FileUtils(context)
}