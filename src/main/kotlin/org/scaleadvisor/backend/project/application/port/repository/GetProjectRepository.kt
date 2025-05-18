package org.scaleadvisor.backend.project.application.port.repository

import org.scaleadvisor.backend.project.domain.Project

interface GetProjectRepository {

    fun findAll(userId: Long): List<Project>

}