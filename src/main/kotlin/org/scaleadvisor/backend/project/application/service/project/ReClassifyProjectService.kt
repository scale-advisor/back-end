package org.scaleadvisor.backend.project.application.service.project

import org.scaleadvisor.backend.project.application.port.usecase.project.AnalyzeProjectUseCase
import org.scaleadvisor.backend.project.application.port.usecase.project.ReClassifyProjectUseCase
import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.infrastructure.job.AnalysisStage
import org.springframework.stereotype.Service

@Service
private class ReClassifyProjectService(
    private val analyzeProjectUseCase: AnalyzeProjectUseCase
): ReClassifyProjectUseCase {
    override fun invoke(projectVersion: ProjectVersion,
                        initialStage: AnalysisStage,
                        onlyClassify: Boolean): String {
        return analyzeProjectUseCase.invoke(projectVersion, initialStage, onlyClassify)
    }
}