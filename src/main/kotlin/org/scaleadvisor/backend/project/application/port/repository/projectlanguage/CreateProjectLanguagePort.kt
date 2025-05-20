package org.scaleadvisor.backend.project.application.port.repository.projectlanguage

import org.scaleadvisor.backend.project.domain.ProjectLanguage

fun interface CreateProjectLanguagePort {

    fun createAll(projectLanguageList: List<ProjectLanguage>)

}