package org.scaleadvisor.backend.project.application.port.repository.requirement

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.Requirement
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.id.RequirementId

interface GetRequirementPort {

    fun findAll(projectVersion: ProjectVersion): List<Requirement>

    fun findAll(projectVersion: ProjectVersion, requirementNumberPrefixList: List<String>): List<Requirement>

    fun findAllIdList(projectId: ProjectId): List<RequirementId>

    fun findAllIdList(
        projectId: ProjectId,
        versionMajorNumber: Int
    ): List<RequirementId>

    fun findAllIdList(projectVersion: ProjectVersion): List<RequirementId>

}