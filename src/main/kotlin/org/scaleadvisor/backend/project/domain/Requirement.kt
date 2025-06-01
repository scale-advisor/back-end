package org.scaleadvisor.backend.project.domain

import ProjectVersionId
import org.scaleadvisor.backend.project.domain.id.RequirementId

data class Requirement(
    val id: RequirementId,
    val projectVersionId: ProjectVersionId,
    var number: String,
    var name: String,
    var definition: String,
    var detail: String,
    var type: String,
) {
    fun update(
        number: String,
        name: String,
        definition: String,
        detail: String,
        type: String,
    ) {
        this.number = number
        this.name = name
        this.definition = definition
        this.detail = detail
        this.type = type
    }
}