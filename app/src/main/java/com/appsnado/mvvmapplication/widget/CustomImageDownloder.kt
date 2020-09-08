package com.appsnado.mvvmapplication.widget

import android.content.Context
import com.nostra13.universalimageloader.core.download.ImageDownloader
import java.io.InputStream

class CustomImageDownloder(context: Context) : ImageDownloader {
    override fun getStream(imageUri: String?, extra: Any?): InputStream {
        TODO("Not yet implemented")
    }

}
