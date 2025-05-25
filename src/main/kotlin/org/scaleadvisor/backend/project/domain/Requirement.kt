package org.scaleadvisor.backend.project.domain

data class Requirement(
    val number: String,
    val name: String,
    val detailNumber: String,
    val detail: String,
    val type: String,
    val note: String,
)