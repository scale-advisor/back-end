package org.scaleadvisor.backend.project.application.service.version

import org.scaleadvisor.backend.project.application.port.usecase.requirement.CreateRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess.CreateRequirementUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirementunitprocess.GetRequirementUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.CreateUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.CreateProjectVersionUseCase
import org.scaleadvisor.backend.project.application.port.usecase.version.GenerateProjectVersionUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.Requirement
import org.scaleadvisor.backend.project.domain.RequirementUnitProcess
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.domain.id.RequirementId
import org.scaleadvisor.backend.project.domain.id.UnitProcessId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class CreateProjectVersionService(
    private val generateProjectVersionUseCase: GenerateProjectVersionUseCase,
    private val createRequirementUseCase: CreateRequirementUseCase,
    private val createUnitProcessUseCase: CreateUnitProcessUseCase,
    private val createRequirementUnitProcessUseCase: CreateRequirementUnitProcessUseCase,
    private val getRequirementUnitProcessUseCase: GetRequirementUnitProcessUseCase,
) : CreateProjectVersionUseCase {
    override fun create(command: CreateProjectVersionUseCase.Command): ProjectVersion {
        val projectVersion = generateProjectVersionUseCase.generateNextMinorVersion(command.projectVersion)

        val previousRequirementIdList: List<String> =
            command.requirementList.mapNotNull { it.requirementId }
        val previousRequirementIdMap: Map<String, RequirementId> =
            previousRequirementIdList.associateWith { RequirementId.newId() }

        val requirementList: List<Requirement> = command.requirementList.map { requirementDTO ->
            val requirementId: RequirementId =
                previousRequirementIdMap[requirementDTO.requirementId] ?: RequirementId.newId()
            Requirement(
                id = requirementId,
                projectVersionId = projectVersion.id,
                number = requirementDTO.requirementNumber,
                name = requirementDTO.requirementName,
                definition = requirementDTO.requirementDefinition,
                detail = requirementDTO.requirementDetail,
                type = requirementDTO.requirementType,
            )
        }

        createRequirementUseCase.createAll(requirementList)

        val previousUnitProcessIdList: List<String> =
            command.unitProcessList.mapNotNull { it.unitProcessId }
        val previousUnitProcessIdMap: Map<String, UnitProcessId> =
            previousUnitProcessIdList.associateWith { UnitProcessId.newId() }

        val unitProcessList: List<UnitProcess> = command.unitProcessList.map { unitProcessDTO ->
            val unitProcessId: UnitProcessId =
                previousUnitProcessIdMap[unitProcessDTO.unitProcessId] ?: UnitProcessId.newId()
            UnitProcess(
                id = unitProcessId,
                projectVersionId = projectVersion.id,
                name = unitProcessDTO.unitProcessName,
                functionType = unitProcessDTO.functionType,
                isAmbiguous = unitProcessDTO.isAmbiguous,
            )
        }

        createUnitProcessUseCase.createAll(unitProcessList)

        val previousRequirementUnitProcessList: List<RequirementUnitProcess> =
            getRequirementUnitProcessUseCase.findAll(previousRequirementIdList.map {
                RequirementId.from(it)
            })
        val requirementUnitProcessList = previousRequirementUnitProcessList.map { previousRequirementUnitProcess ->
            val requirementId: RequirementId =
                previousRequirementIdMap[previousRequirementUnitProcess.requirementId.toString()]
                    ?: throw IllegalArgumentException("requirement_unit_process_id is missing")

            val unitProcessId: UnitProcessId =
                previousUnitProcessIdMap[previousRequirementUnitProcess.unitProcessId.toString()]
                    ?: throw IllegalArgumentException("requirement_unit_process_id is missing")

            RequirementUnitProcess(
                requirementId = requirementId,
                unitProcessId = unitProcessId,
            )
        }

        createRequirementUnitProcessUseCase.createAll(requirementUnitProcessList)

        return projectVersion
    }
}