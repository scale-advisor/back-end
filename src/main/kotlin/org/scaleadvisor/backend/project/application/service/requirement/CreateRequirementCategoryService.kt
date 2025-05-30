package org.scaleadvisor.backend.project.application.service.requirement

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.requirementcategory.CreateRequirementCategoryPort
import org.scaleadvisor.backend.project.application.port.usecase.requirement.CreateRequirementCategoryUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.GetProjectVersionUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.RequirementCategory
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.id.RequirementCategoryId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class CreateRequirementCategoryService(
    private val getProjectVersionUseCase: GetProjectVersionUseCase,
    private val createRequirementCategoryPort : CreateRequirementCategoryPort
) : CreateRequirementCategoryUseCase {
    override fun createAll(
        projectId: ProjectId,
        requirementCategoryDTOList: List<CreateRequirementCategoryUseCase.RequirementCategoryDTO>
    ) {
        val projectVersion: ProjectVersion = getProjectVersionUseCase.findLatest(projectId)
            ?: throw NotFoundException("Project version not found")

        val requirementCategoryList = requirementCategoryDTOList.map { requirementCategoryDTO ->
            RequirementCategory(
                id = RequirementCategoryId.newId(),
                projectVersionId = projectVersion.id,
                name = requirementCategoryDTO.name,
                prefix =  requirementCategoryDTO.prefix,
            )
        }
        createRequirementCategoryPort.createAll(requirementCategoryList)

    }

}