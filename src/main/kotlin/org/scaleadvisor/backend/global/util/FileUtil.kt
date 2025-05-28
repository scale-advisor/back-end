package org.scaleadvisor.backend.global.util

import org.springframework.web.multipart.MultipartFile
import java.util.*

object FileUtil {
    @JvmStatic
    fun getExtension(file: MultipartFile): String {
        val fileName = requireNotNull(file.originalFilename) { "File name is null" }

        val dotIndex = fileName.lastIndexOf('.')
        require(!(dotIndex == -1 || dotIndex == fileName.length - 1)) {
            "File name has no extension"
        }

        return fileName.substring(dotIndex + 1)
    }

    @JvmStatic
    fun fileValidator(file: MultipartFile) {
        require(!(file.isEmpty || Objects.isNull(file.originalFilename))) { "File is empty" }
    }

}
