package org.scaleadvisor.backend.project.api.request

import org.scaleadvisor.backend.project.domain.enum.CocomoType

data class CreateProjectOptionRequest(
    var unitCost: Int,
    var teamSize: Int,
    var cocomoType: CocomoType,
    var languageList: List<String>
)
