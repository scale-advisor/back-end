package org.scaleadvisor.backend.project.application.port.usecase.project

import org.scaleadvisor.backend.project.domain.ProjectVersion
import org.scaleadvisor.backend.project.infrastructure.job.AnalysisStage

fun interface ReClassifyProjectUseCase {
    fun invoke(projectVersion: ProjectVersion,
               initialStage: AnalysisStage,
               onlyClassify: Boolean): String
}