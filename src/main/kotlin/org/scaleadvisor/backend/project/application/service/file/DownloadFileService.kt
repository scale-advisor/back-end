package org.scaleadvisor.backend.project.application.service.file

import org.scaleadvisor.backend.project.application.port.repository.file.DownloadFilePort
import org.scaleadvisor.backend.project.application.port.usecase.file.DownloadFileUseCase
import org.springframework.stereotype.Service


@Service
private class DownloadFileService(
    private val downloadFilePort: DownloadFilePort,
) : DownloadFileUseCase {
    override fun download(path: String): ByteArray {
        return downloadFilePort.download(path)
    }



}