package org.scaleadvisor.backend.project.domain

import ProjectVersionId
import org.scaleadvisor.backend.project.domain.id.RequirementId

data class Requirement(
    val id: RequirementId,
    val projectVersionId: ProjectVersionId,
    val number: String,
    val name: String,
    val definition: String,
    val detailNumber: String,
    val detail: String,
    val type: String,
    val note: String,
)