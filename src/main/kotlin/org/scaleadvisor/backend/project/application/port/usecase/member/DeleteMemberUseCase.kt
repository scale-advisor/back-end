package org.scaleadvisor.backend.project.application.port.usecase.member

fun interface DeleteMemberUseCase {
    fun delete(email: String, projectId: Long)
}