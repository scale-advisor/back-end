package org.scaleadvisor.backend.project.application.service.requirement

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.requirement.CreateRequirementPort
import org.scaleadvisor.backend.project.application.port.usecase.requirement.CreateRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirement.CreateRequirementUseCase.RequirementDTO
import org.scaleadvisor.backend.project.application.port.usecase.version.GetProjectVersionUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.Requirement
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class CreateRequirementService(
    private val getProjectVersionUseCase: GetProjectVersionUseCase,
    private val createRequirementPort: CreateRequirementPort,
) : CreateRequirementUseCase {
    override fun createAll(
        projectId: ProjectId,
        requirementDTOList: List<RequirementDTO>
    ) {
        val projectVersion: ProjectVersion = getProjectVersionUseCase.findLatest(projectId)
            ?: throw NotFoundException("Project version not found")

        val requirementList =  requirementDTOList.map {
            Requirement(
                id = RequirementId.newId(),
                projectVersionId = projectVersion.id,
                number = it.number,
                name = it.name,
                definition = it.definition,
                detail = it.detail,
                type = it.type,
            )
        }

        createRequirementPort.createAll(requirementList)
    }
}