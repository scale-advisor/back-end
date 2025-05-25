package org.scaleadvisor.backend.project.domain

data class ProjectRequirement(
    val number: String,
    val name: String,
    val detailNumber: String,
    val detail: String,
    val type: String,
    val note: String,
)