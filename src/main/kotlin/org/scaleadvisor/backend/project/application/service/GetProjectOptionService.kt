package org.scaleadvisor.backend.project.application.service

import org.scaleadvisor.backend.global.exception.model.NotFoundException
import org.scaleadvisor.backend.project.application.port.usecase.GetProjectOptionUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectfactor.GetProjectFactorUseCase
import org.scaleadvisor.backend.project.application.port.usecase.projectlanguage.GetProjectLanguageUseCase
import org.scaleadvisor.backend.project.domain.ProjectFactor
import org.scaleadvisor.backend.project.domain.id.ProjectId
import org.springframework.stereotype.Service

@Service
class GetProjectOptionService(
    private val getProjectFactorUseCase: GetProjectFactorUseCase,
    private val getProjectLanguageUseCase: GetProjectLanguageUseCase
) : GetProjectOptionUseCase {
    override fun find(projectId: ProjectId): GetProjectOptionUseCase.Result {
        val projectFactor: ProjectFactor = getProjectFactorUseCase.find(projectId)
            ?: throw NotFoundException("ProjectFactor not found")
        val projectLanguageList = getProjectLanguageUseCase.findAll(projectId)

        return GetProjectOptionUseCase.Result(
            projectFactor,
            projectLanguageList
        )
    }
}