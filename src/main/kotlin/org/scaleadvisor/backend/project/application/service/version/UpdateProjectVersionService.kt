package org.scaleadvisor.backend.project.application.service.version

import org.scaleadvisor.backend.project.application.port.usecase.requirement.CreateRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirement.DeleteRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess.CreateRequirementUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess.GetRequirementUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.CreateUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.DeleteUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.UpdateProjectVersionUseCase
import org.scaleadvisor.backend.project.domain.Requirement
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.scaleadvisor.backend.project.domain.id.UnitProcessId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class UpdateProjectVersionService(
    private val getRequirementUnitProcessUseCase: GetRequirementUnitProcessUseCase,
    private val deleteRequirementUseCase: DeleteRequirementUseCase,
    private val deleteUnitProcessUseCase: DeleteUnitProcessUseCase,
    private val createRequirementUseCase: CreateRequirementUseCase,
    private val createUnitProcessUseCase: CreateUnitProcessUseCase,
    private val createRequirementUnitProcessUseCase: CreateRequirementUnitProcessUseCase,
//    private val updateAdjustmentFactorUseCase: UpdateAdjustmentFactorUseCase,
) : UpdateProjectVersionUseCase {
    override fun update(command: UpdateProjectVersionUseCase.Command) {

        val requirementList: List<Requirement> = command.requirementList.map { requirementDTO ->
            val requirementId: RequirementId = requirementDTO.requirementId
                .takeUnless { it.isNullOrEmpty() }
                ?.let { RequirementId.from(it) }
                ?: RequirementId.newId()

            Requirement(
                id = requirementId,
                projectVersionId = command.projectVersion.id,
                number = requirementDTO.requirementNumber,
                name = requirementDTO.requirementName,
                definition = requirementDTO.requirementDefinition,
                detail = requirementDTO.requirementDetail,
                type = requirementDTO.requirementType,
            )
        }

        val unitProcessList: List<UnitProcess> = command.unitProcessList.map { unitProcessDTO ->
            val unitProcessId: UnitProcessId = unitProcessDTO.unitProcessId
                .takeUnless { it.isNullOrEmpty() }
                ?.let { UnitProcessId.from(it) }
                ?: UnitProcessId.newId()

            UnitProcess(
                id = unitProcessId,
                projectVersionId = command.projectVersion.id,
                name = unitProcessDTO.unitProcessName,
                functionType = unitProcessDTO.functionType,
                isAmbiguous = unitProcessDTO.isAmbiguous,
            )
        }

        val requirementUnitProcessList = getRequirementUnitProcessUseCase.findAll(requirementList.map { it.id })

        deleteRequirementUseCase.deleteAll(command.projectVersion)
        deleteUnitProcessUseCase.deleteAll(command.projectVersion)

        createRequirementUseCase.createAll(requirementList)
        createUnitProcessUseCase.createAll(unitProcessList)
        createRequirementUnitProcessUseCase.createAll(requirementUnitProcessList)

//        updateAdjustmentFactorUseCase.updateAll(command.adjustmentFactorList.map {
//            UpdateAdjustmentFactorDTO(
//                id = AdjustmentFactorId.from(it.adjustmentFactorId),
//                level = it.adjustmentFactorLevel,
//            )
//        })
    }
}