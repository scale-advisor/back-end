package org.scaleadvisor.backend.project.application.service.version

import org.scaleadvisor.backend.project.application.port.repository.version.DeleteProjectVersionPort
import org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor.DeleteAdjustmentFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.file.DeleteFileUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirement.DeleteRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirement.GetRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirementcategory.DeleteRequirementCategoryUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess.GetRequirementUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.DeleteUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.DeleteProjectVersionUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.scaleadvisor.backend.project.domain.id.UnitProcessId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class DeleteProjectVersionService(
    private val deleteAdjustmentFactorUseCase: DeleteAdjustmentFactorUseCase,
    private val deleteRequirementCategoryUseCase: DeleteRequirementCategoryUseCase,
    private val getRequirementUseCase: GetRequirementUseCase,
    private val getRequirementUnitProcessUseCase: GetRequirementUnitProcessUseCase,
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

        deleteAdjustmentFactorUseCase.deleteAll(projectVersion)
        deleteRequirementCategoryUseCase.deleteAll(projectVersion)
        if (projectVersion.minor == 0) {
            val requirementIdList: List<RequirementId> = getRequirementUseCase.findAllId(
                projectId,
                projectVersion.major
            )
            this.deleteAllByRequirementIdList(requirementIdList)

            deleteFileUseCase.delete(projectVersion)
            deleteProjectVersionPort.deleteAll(projectId, projectVersion.major)
        } else {
            val requirementIdList: List<RequirementId> =
                getRequirementUseCase.findAllId(projectVersion)
            this.deleteAllByRequirementIdList(requirementIdList)

            deleteProjectVersionPort.delete(projectVersion)
        }
    }

    override fun deleteAll(projectId: ProjectId) {
        deleteAdjustmentFactorUseCase.deleteAll(projectId)
        deleteRequirementCategoryUseCase.deleteAll(projectId)

        val requirementIdList: List<RequirementId> = getRequirementUseCase.findAllId(projectId)

        this.deleteAllByRequirementIdList(requirementIdList)

        deleteFileUseCase.deleteAll(projectId)
        deleteProjectVersionPort.deleteAll(projectId)
    }

    private fun deleteAllByRequirementIdList(requirementIdList: List<RequirementId>) {
        val unitProcessIdList: List<UnitProcessId> =
            getRequirementUnitProcessUseCase.findAllDistinctUnitProcessId(requirementIdList)

        deleteRequirementUseCase.deleteAll(requirementIdList)
        deleteUnitProcessUseCase.deleteAll(unitProcessIdList)
    }
}