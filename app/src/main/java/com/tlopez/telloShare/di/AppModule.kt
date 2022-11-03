package com.tlopez.telloShare.di

import com.tlopez.telloShare.data.repository.TelloRepositoryLegacyImpl
import com.tlopez.telloShare.domain.models.TelloRepositoryLegacy
import com.tlopez.telloShare.util.InputValidationUtil
import com.tlopez.telloShare.util.MediaCodecH624
import com.tlopez.telloShare.util.TelloStateUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*
    @Singleton
    @Provides
    fun provideSocketServiceRepository(
        telloStateUtil: TelloStateUtil,
        mediaCodecH624: MediaCodecH624
    ): TelloRepositoryLegacy =
        TelloRepositoryLegacyImpl(telloStateUtil, mediaCodecH624)

    @Provides
    fun provideTelloStateLegacyUtil(): TelloStateLegacyUtil = TelloStateUtil()


     */
    @Provides
    fun provideMediaCodecH624(): MediaCodecH624 = MediaCodecH624()

    @Provides
    fun provideInputValidationUtil(): InputValidationUtil = InputValidationUtil()
}