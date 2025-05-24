package org.scaleadvisor.backend.project.application.port.repository.file

import org.scaleadvisor.backend.project.domain.File

fun interface CreateFilePort {
    fun create(file: File)
}