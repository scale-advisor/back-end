package org.scaleadvisor.backend.project.domain

import org.scaleadvisor.backend.project.domain.enum.ProgramLanguage

data class ProjectLanguage(
    val language: ProgramLanguage,
    val rate: Int
)