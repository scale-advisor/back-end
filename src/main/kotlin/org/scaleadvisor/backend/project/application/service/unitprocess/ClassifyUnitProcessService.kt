package org.scaleadvisor.backend.project.application.service.unitprocess

import org.scaleadvisor.backend.project.application.port.repository.unitprocess.ClassifyUnitProcessPort
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.ClassifyUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.GetUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class ClassifyUnitProcessService(
    private val getUnitProcessUseCase: GetUnitProcessUseCase,
    private val classifyUnitProcess: ClassifyUnitProcessPort
): ClassifyUnitProcessUseCase {
    fun execute(projectVersion: ProjectVersion): List<UnitProcess> {
        val uintProcessList: List<UnitProcess> = getUnitProcessUseCase.findAll(projectVersion)
        return classifyUnitProcess(uintProcessList)
    }

    override fun invoke(projectVersion: ProjectVersion): List<UnitProcess> {
        return this.execute(projectVersion)
    }

}