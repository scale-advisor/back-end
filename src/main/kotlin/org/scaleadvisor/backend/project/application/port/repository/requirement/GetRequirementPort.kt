package org.scaleadvisor.backend.project.application.port.repository.requirement

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.Requirement

interface GetRequirementPort {

    fun findAll(projectVersion: ProjectVersion): List<Requirement>

}