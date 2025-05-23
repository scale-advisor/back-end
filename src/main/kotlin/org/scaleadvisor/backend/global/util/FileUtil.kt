package org.scaleadvisor.backend.global.util

import org.springframework.web.multipart.MultipartFile

object FileUtil {
    fun getExtension(file: MultipartFile): String {
        val fileName = file.originalFilename
        if (fileName == null) {
            throw IllegalArgumentException("File name is null")
        }
        val dotIndex = fileName.lastIndexOf('.')
        if (dotIndex == -1 || dotIndex == fileName.length - 1) {
            throw IllegalArgumentException("File name has no extension")
        }
        return fileName.substring(dotIndex + 1)
    }
}
