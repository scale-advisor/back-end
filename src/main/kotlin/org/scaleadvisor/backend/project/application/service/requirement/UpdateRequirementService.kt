package org.scaleadvisor.backend.project.application.service.requirement

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.requirement.UpdateRequirementPort
import org.scaleadvisor.backend.project.application.port.usecase.requirement.GetRequirementUseCase
import org.scaleadvisor.backend.project.application.port.usecase.requirement.UpdateRequirementUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class UpdateRequirementService(
    private val getRequirementUseCase: GetRequirementUseCase,
    private val updateRequirementPort: UpdateRequirementPort,
) : UpdateRequirementUseCase {
    override fun updateAll(requirementDTOList: List<UpdateRequirementUseCase.RequirementDTO>) {
        val requirementDTOIdMap = requirementDTOList.associateBy { it.id }

        val requirementList = getRequirementUseCase.findAll(requirementDTOList.map { it.id })

        requirementList.forEach {
            val requirementDTO = requirementDTOIdMap[it.id]
                ?: throw NotFoundException("업데이트 할 요구사항 중 없는 요구사항이 있습니다. requirementId=${it.id}")
            it.update(
                requirementDTO.number,
                requirementDTO.name,
                requirementDTO.definition,
                requirementDTO.detail,
                requirementDTO.type,
            )
        }

        updateRequirementPort.updateAll(requirementList)
    }

}