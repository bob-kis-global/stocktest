package com.example.stocktest.utils

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels

object FileUtil {
    fun downloadFile(context: Context, url: URL, fileName: String) {
        url.openStream().use {
            Channels.newChannel(it).use { rbc ->
                FileOutputStream(File(context.cacheDir, fileName)).use { fos ->
                    fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                }
            }
        }
    }

    fun readFile(context: Context, fileName: String): String {
        return FileInputStream(File(context.cacheDir, fileName)).bufferedReader().use {
            it.readText()
        }
    }
}