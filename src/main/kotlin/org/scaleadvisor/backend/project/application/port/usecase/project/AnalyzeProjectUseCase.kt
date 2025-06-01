package org.scaleadvisor.backend.project.application.port.usecase.project

import org.scaleadvisor.backend.project.domain.ProjectVersion

interface AnalyzeProjectUseCase {

    operator fun invoke(projectVersion: ProjectVersion): String

}