package org.scaleadvisor.backend.project.application.service.requirementcategory

import org.scaleadvisor.backend.project.application.port.repository.requirementcategory.DeleteRequirementCategoryPort
import org.scaleadvisor.backend.project.application.port.usecase.requirementcategory.DeleteRequirementCategoryUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteRequirementCategoryService(
    private val deleteRequirementCategoryPort: DeleteRequirementCategoryPort,
): DeleteRequirementCategoryUseCase {

    override fun deleteAll(projectId: ProjectId) {
        deleteRequirementCategoryPort.deleteAll(projectId)

    }

    override fun deleteAll(projectVersion: ProjectVersion) {
        if (projectVersion.minor == 0) {
            deleteRequirementCategoryPort.deleteAll(
                projectId = projectVersion.projectId,
                versionMajorNumber = projectVersion.major
            )
        } else {
            deleteRequirementCategoryPort.deleteAll(projectVersion)
        }
    }

}