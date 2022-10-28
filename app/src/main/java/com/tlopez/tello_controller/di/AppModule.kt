package com.tlopez.tello_controller.di

import android.content.Context
import com.tlopez.tello_controller.data.repository.AuthRepositoryImpl
import com.tlopez.tello_controller.data.repository.TelloRepositoryImpl
import com.tlopez.tello_controller.domain.models.TelloRepository
import com.tlopez.tello_controller.domain.repository.AuthRepository
import com.tlopez.tello_controller.util.InputValidationUtil
import com.tlopez.tello_controller.util.MediaCodecH624
import com.tlopez.tello_controller.util.TelloStateUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthRepository(@ApplicationContext applicationContext: Context) : AuthRepository {
        return AuthRepositoryImpl(applicationContext)
    }

    @Singleton
    @Provides
    fun provideSocketServiceRepository(
        telloStateUtil: TelloStateUtil,
        mediaCodecH624: MediaCodecH624
    ): TelloRepository =
        TelloRepositoryImpl(telloStateUtil, mediaCodecH624)

    @Provides
    fun provideTelloStateUtil(): TelloStateUtil = TelloStateUtil()

    @Provides
    fun provideMediaCodecH624(): MediaCodecH624 = MediaCodecH624()

    @Provides
    fun provideInputValidationUtil(): InputValidationUtil = InputValidationUtil()
}