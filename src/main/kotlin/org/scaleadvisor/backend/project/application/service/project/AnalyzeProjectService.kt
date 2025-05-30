package org.scaleadvisor.backend.project.application.service.project

import org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor.AnalyzeAdjustmentFactorLevelUseCase
import org.scaleadvisor.backend.project.application.port.usecase.adjustmentfactor.CreateAdjustmentFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.AnalyzeProjectUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.ClassifyUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.ETLUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.UpdateUnitProcessUseCase
import org.scaleadvisor.backend.project.application.port.usecase.unitprocess.ValidateUnitProcessUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
private class AnalyzeProjectService(
    private val etlUnitProcess: ETLUnitProcessUseCase,
    private val validateUnitProcess: ValidateUnitProcessUseCase,
    private val classifyUnitProcess: ClassifyUnitProcessUseCase,
    private val updateUnitProcessUseCase: UpdateUnitProcessUseCase,
    private val analyzeAdjustmentFactorLevelUseCase: AnalyzeAdjustmentFactorLevelUseCase,
    private val createAdjustmentFactorUseCase: CreateAdjustmentFactorUseCase,
): AnalyzeProjectUseCase {

    override fun invoke(projectVersion: ProjectVersion) {
        etlUnitProcess(projectVersion)
        validateUnitProcess(projectVersion)

        val unitProcessList: List<UnitProcess> = classifyUnitProcess(projectVersion)
        updateUnitProcessUseCase.updateAll(unitProcessList)

        val adjustmentFactorList = analyzeAdjustmentFactorLevelUseCase(projectVersion)
        createAdjustmentFactorUseCase.createAll(adjustmentFactorList)
    }


}