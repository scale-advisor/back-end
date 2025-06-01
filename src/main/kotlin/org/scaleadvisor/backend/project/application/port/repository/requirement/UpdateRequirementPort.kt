package org.scaleadvisor.backend.project.application.port.repository.requirement

import org.scaleadvisor.backend.project.domain.Requirement

fun interface UpdateRequirementPort {
    fun updateAll(requirementList: List<Requirement>)
}