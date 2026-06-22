package com.example.GridSync.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.content.FileProvider
import java.io.File

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

fun shareFile(
    context: Context,
    file: File
) {

    val uri =
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

    val intent =
        Intent(Intent.ACTION_SEND).apply {
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

    context.startActivity(
        Intent.createChooser(
            intent,
            "Share DSM Report"
        )
    )
}