package org.scaleadvisor.backend.project.application.service.project

import org.scaleadvisor.backend.project.application.port.repository.unitprocess.GetUnitProcessPort
import org.scaleadvisor.backend.project.application.port.repository.unitprocess.UpdateUnitProcessPort
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.UpdateUndefinedUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.enum.FunctionType
import org.springframework.stereotype.Service

@Service
private class UpdateUndefinedUnitProcessService(
    private val getUnitProcessPort: GetUnitProcessPort,
    private val updateUnitProcessPort: UpdateUnitProcessPort
): UpdateUndefinedUnitProcessUseCase {

    override fun markAmbiguousForUndefined(projectVersion: ProjectVersion) {
        val allUnitProcesses = getUnitProcessPort.findAll(projectVersion)

        val undefinedIds = allUnitProcesses
            .filter { it.functionType == FunctionType.UNDEFINED }
            .map { it.id }

        if (undefinedIds.isNotEmpty()) {
            updateUnitProcessPort.updateAllIsAmbiguous(undefinedIds)
        }
    }
}