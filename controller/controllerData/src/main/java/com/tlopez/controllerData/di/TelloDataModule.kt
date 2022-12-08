package com.tlopez.controllerData.di

import android.content.Context
import android.media.MediaCodec
import com.tlopez.controllerData.FileRepositoryImpl
import com.tlopez.controllerData.MediaCodecH624
import com.tlopez.controllerData.TelloRepositoryImpl
import com.tlopez.controllerData.TelloStateUtil
import com.tlopez.controllerDomain.FileRepository
import com.tlopez.controllerDomain.TelloRepository
import com.tlopez.controllerDomain.TelloState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TelloDataModule {
    @Provides
    @Singleton
    fun provideTelloStateUtil(): TelloStateUtil = TelloStateUtil()

    @Provides
    @Singleton
    fun provideTelloRepository(
        telloStateUtil: TelloStateUtil,
        ioDispatcher: CoroutineDispatcher,
        codec: MediaCodec
    ): TelloRepository {
        return TelloRepositoryImpl(
            telloStateUtil,
            ioDispatcher,
            codec
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO.limitedParallelism(1)

    @Provides
    @Singleton
    fun provideCodec(): MediaCodec = MediaCodecH624().codec

    @Provides
    @Singleton
    fun provideFileRepository(
        @ApplicationContext context: Context
    ): FileRepository = FileRepositoryImpl(context)
}