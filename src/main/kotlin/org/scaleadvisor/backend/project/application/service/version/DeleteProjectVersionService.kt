package org.scaleadvisor.backend.project.application.service.version

import org.scaleadvisor.backend.project.application.port.repository.version.DeleteProjectVersionPort
import org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor.DeleteAdjustmentFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.file.DeleteFileUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirement.DeleteRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirementcategory.DeleteRequirementCategoryUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.DeleteUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.DeleteProjectVersionUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteProjectVersionService(
    private val deleteAdjustmentFactorUseCase: DeleteAdjustmentFactorUseCase,
    private val deleteRequirementCategoryUseCase: DeleteRequirementCategoryUseCase,
    private val deleteRequirementUseCase: DeleteRequirementUseCase,
    private val deleteUnitProcessUseCase: DeleteUnitProcessUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
    private val deleteProjectVersionPort: DeleteProjectVersionPort,
) : DeleteProjectVersionUseCase {

    override fun delete(
        projectId: ProjectId,
        versionNumber: String
    ) {
        val projectVersion = ProjectVersion.of(projectId, versionNumber)

        if (projectVersion.minor == 0) {
            deleteAdjustmentFactorUseCase.deleteAll(projectId, projectVersion.major)

            deleteRequirementCategoryUseCase.deleteAll(projectId, projectVersion.major)

            deleteRequirementUseCase.deleteAll(projectId, projectVersion.major)
            deleteUnitProcessUseCase.deleteAll(projectId, projectVersion.major)

            deleteFileUseCase.delete(projectVersion)
            deleteProjectVersionPort.deleteAll(projectId, projectVersion.major)
        } else {
            deleteAdjustmentFactorUseCase.deleteAll(projectVersion)

            deleteRequirementCategoryUseCase.deleteAll(projectVersion)

            deleteRequirementUseCase.deleteAll(projectVersion)
            deleteUnitProcessUseCase.deleteAll(projectVersion)

            deleteProjectVersionPort.delete(projectVersion)
        }
    }

    override fun deleteAll(projectId: ProjectId) {
        deleteAdjustmentFactorUseCase.deleteAll(projectId)

        deleteRequirementCategoryUseCase.deleteAll(projectId)
        deleteRequirementUseCase.deleteAll(projectId)
        deleteUnitProcessUseCase.deleteAll(projectId)

        deleteFileUseCase.deleteAll(projectId)
        deleteProjectVersionPort.deleteAll(projectId)
    }

}