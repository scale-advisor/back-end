package org.scaleadvisor.backend.project.application.port.repository.requirement

import org.scaleadvisor.backend.project.domain.id.RequirementId

interface DeleteRequirementPort {

    fun deleteAll(requirementIdList: List<RequirementId>)

}