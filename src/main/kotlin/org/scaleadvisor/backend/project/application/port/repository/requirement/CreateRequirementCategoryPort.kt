package org.scaleadvisor.backend.project.application.port.repository.requirement

import org.scaleadvisor.backend.project.domain.RequirementCategory

fun interface CreateRequirementCategoryPort {

    fun createAll(requirementCategoryList: List<RequirementCategory>)

}