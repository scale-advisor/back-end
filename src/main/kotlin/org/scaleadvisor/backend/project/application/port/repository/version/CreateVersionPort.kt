package org.scaleadvisor.backend.project.application.port.repository.version

import org.scaleadvisor.backend.project.domain.Version

fun interface CreateVersionPort {

    fun create(version: Version)

}