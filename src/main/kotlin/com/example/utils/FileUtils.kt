package com.example.utils

import java.io.File
import java.net.URLConnection

object FileUtils {
    fun saveByteArrayToFile(byteArray: ByteArray, filePath: String): File {
        val file = File(filePath)
        file.writeBytes(byteArray)
        return file
    }
}