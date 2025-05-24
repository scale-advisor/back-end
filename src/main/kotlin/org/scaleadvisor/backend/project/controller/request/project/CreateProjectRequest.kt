package org.scaleadvisor.backend.project.controller.request.project

data class CreateProjectRequest(
    val name: String,
    val description: String?
)