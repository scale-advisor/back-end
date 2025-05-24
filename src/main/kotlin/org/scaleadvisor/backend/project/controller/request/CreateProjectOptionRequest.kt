package org.scaleadvisor.backend.project.controller.request

import org.scaleadvisor.backend.project.domain.enum.CocomoType

data class CreateProjectOptionRequest(
    var unitCost: Int,
    var teamSize: Int,
    var cocomoType: CocomoType,
    var languageList: List<String>
)
