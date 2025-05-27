package org.scaleadvisor.backend.project.application.port.usecase.member

interface DeleteMemberUseCase {
    fun delete(email: String, projectId: Long)
    fun delete(projectId: Long)
}