package com.yamaplite.utils

import android.graphics.Bitmap
import android.util.LruCache
import android.util.Log
import java.lang.Runtime

object ImageCache {

  private val cacheSize = 50 * 1024 * 1024 // 50 МБ в байтах

  private val memoryCache: LruCache<String, Bitmap> = object : LruCache<String, Bitmap>(cacheSize) {
    override fun sizeOf(key: String, value: Bitmap): Int {
      return value.byteCount // размер в байтах
    }
  }

  /** Получить Bitmap из кэша по URL */
  fun get(url: String): Bitmap? {
    val bitmap = memoryCache.get(url)
    if (bitmap != null) {
      Log.d("ImageCache", "Cache HIT for: $url (size: ${bitmap.byteCount} bytes)")
    } else {
      Log.d("ImageCache", "Cache MISS for: $url")
    }
    return bitmap
  }

  /** Положить Bitmap в кэш по URL */
  fun put(url: String, bitmap: Bitmap) {
    val size = bitmap.byteCount
    memoryCache.put(url, bitmap)
    Log.d("ImageCache", "Cached image: $url (size: $size bytes, cache size: ${memoryCache.size()} bytes)")
  }

  /** Проверка наличия Bitmap в кэше */
  fun contains(url: String): Boolean {
    return memoryCache.get(url) != null
  }

  /** Очистить весь кэш */
  fun clear() {
    memoryCache.evictAll()
  }
}
