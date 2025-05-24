package org.scaleadvisor.backend.project.application.port.repository.file

fun interface DownloadFilePort {

    fun download(path: String): ByteArray

}
