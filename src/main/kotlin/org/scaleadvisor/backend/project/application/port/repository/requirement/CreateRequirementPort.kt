package org.scaleadvisor.backend.project.application.port.repository.requirement

import org.scaleadvisor.backend.project.domain.Requirement

fun interface CreateRequirementPort {

    fun createAll(requirementList: List<Requirement>, )

}