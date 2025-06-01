package org.scaleadvisor.backend.project.application.service.requirement

import org.scaleadvisor.backend.project.application.port.repository.requirement.CreateRequirementPort
import org.scaleadvisor.backend.project.application.port.usecase.requirement.CreateRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirement.CreateRequirementUseCase.RequirementDTO
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.Requirement
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.locks.LockSupport

@Service
@Transactional
private class CreateRequirementService(
    private val createRequirementPort: CreateRequirementPort,
) : CreateRequirementUseCase {
    override fun createAll(requirementList: List<Requirement>) {
        createRequirementPort.createAll(requirementList)
    }

    override fun createAll(
        projectVersion: ProjectVersion,
        requirementDTOList: List<RequirementDTO>
    ) {
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