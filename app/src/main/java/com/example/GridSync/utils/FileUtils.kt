package com.example.GridSync.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

fun getFileName(
    context: Context,
    uri: Uri
): String? {

    var fileName: String? = null

    val cursor =
        context.contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )

    cursor?.use {

        if (it.moveToFirst()) {

            val index =
                it.getColumnIndex(
                    OpenableColumns.DISPLAY_NAME
                )

            if (index >= 0) {
                fileName = it.getString(index)
            }
        }
    }

    return fileName
}