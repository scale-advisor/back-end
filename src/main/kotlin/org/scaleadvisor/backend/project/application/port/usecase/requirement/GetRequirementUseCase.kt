package org.scaleadvisor.backend.project.application.port.usecase.requirement

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.Requirement
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.scaleadvisor.backend.project.domain.id.UnitProcessId

interface GetRequirementUseCase {

    fun findAllFunctionRequirement(projectVersion: ProjectVersion): List<Requirement>

    fun findAll(projectVersion: ProjectVersion, ): List<Requirement>

    fun findAll(
        projectVersion: ProjectVersion,
        requirementNumberPrefixList: List<String>
    ): List<Requirement>

    fun findAll(requirementIdList: List<RequirementId> ): List<Requirement>

    fun findAllId(projectId: ProjectId): List<RequirementId>

    fun findAllId(
        projectId: ProjectId,
        versionMajorNumber: Int
    ): List<RequirementId>

    fun findAllId(projectVersion: ProjectVersion): List<RequirementId>

    fun findAllId(unitProcessId: UnitProcessId): List<RequirementId>

}