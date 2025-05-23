package org.scaleadvisor.backend.project.application.service.cocomomultiplier

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.repository.cocomomultiplier.DeleteCocomoMultiplierPort
import org.scaleadvisor.backend.project.application.port.usecase.cocomomultiplier.DeleteCocomoMultiplierUseCase
import org.scaleadvisor.backend.project.application.port.usecase.cocomomultiplier.FindCocomoMultiplierUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.GetProjectUseCase
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service

@Service
private class DeleteCocomoMultiplierService(
    private val deleteCocomoMultiplierPort: DeleteCocomoMultiplierPort,
    private val findCocomoMultiplierUseCase: FindCocomoMultiplierUseCase,
    private val getProjectUseCase: GetProjectUseCase
): DeleteCocomoMultiplierUseCase {
    override fun delete(projectId: ProjectId) {
        getProjectUseCase.find(projectId)
            ?: throw NotFoundException("프로젝트를 찾을 수 없습니다. (id=${projectId.value})")

        findCocomoMultiplierUseCase.find(projectId)
            ?: throw NotFoundException("삭제할 Cocomo Multiplier가 없습니다. (projectId=${projectId.value})")

        deleteCocomoMultiplierPort.delete(projectId)
    }
}