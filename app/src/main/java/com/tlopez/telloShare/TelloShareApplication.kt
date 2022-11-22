package com.tlopez.telloShare

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.memory.MemoryCache
import coil.request.CachePolicy
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TelloShareApplication : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(500)
            .memoryCache {
                MemoryCache.Builder(applicationContext)
                    .maxSizePercent(1.0)
                    .build()
            }
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
    }
}