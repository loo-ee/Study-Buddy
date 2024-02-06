package com.louie.studybuddy.storage

import android.content.Context
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.StringBuilder

object FileHandler {
    fun writeToFile(context: Context, data: String, path: String) {
        val fileOutputStream = context.openFileOutput(path, Context.MODE_PRIVATE)
        val outputWriter = OutputStreamWriter(fileOutputStream)

        outputWriter.write(data)
        outputWriter.close()
    }

    fun readFromFile(context: Context, path: String): String {
        val data = StringBuilder()
        val fileInputStream = context.openFileInput(path)
        val inputReader = InputStreamReader(fileInputStream)
        val inputBuffer = CharArray(100)
        var charRead: Int

        while (inputReader.read(inputBuffer).also { charRead = it } >  0) {
            val readString = String(inputBuffer,  0, charRead)
            data.append(readString)
        }

        return data.toString()
    }
}