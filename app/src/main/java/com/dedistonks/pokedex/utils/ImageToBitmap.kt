package com.dedistonks.pokedex.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

class ImageToBitmap {
    companion object {
        @JvmStatic
        fun from(url: URL, callback: (Bitmap) -> Unit) {
            Thread.thread {
                val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                callback(bitmap)
            }
        }

        @JvmStatic
        fun from(url: String, callback: (Bitmap) -> Unit) = from(URL(url), callback)
    }
}