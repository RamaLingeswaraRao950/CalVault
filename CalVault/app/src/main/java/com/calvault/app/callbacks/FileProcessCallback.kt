package com.calvault.app.callbacks

import java.io.File

interface FileProcessCallback {
    fun onFilesProcessedSuccessfully(copiedFiles: List<File>)
    fun onFileProcessFailed()
}
