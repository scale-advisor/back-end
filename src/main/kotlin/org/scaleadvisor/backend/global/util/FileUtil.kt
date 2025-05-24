package org.scaleadvisor.backend.global.util

import org.springframework.web.multipart.MultipartFile

object FileUtil {
    fun getExtension(file: MultipartFile): String {
        val fileName = requireNotNull(file.originalFilename) { "File name is null" }

        val dotIndex = fileName.lastIndexOf('.')
        require(!(dotIndex == -1 || dotIndex == fileName.length - 1)) {
            "File name has no extension"
        }

        return fileName.substring(dotIndex + 1)
    }
}
