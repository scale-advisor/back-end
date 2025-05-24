package org.scaleadvisor.backend.project.application.port.usecase.file

fun interface DownloadFileUseCase {

    fun download(path: String): ByteArray

}