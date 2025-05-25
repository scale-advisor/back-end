package org.scaleadvisor.backend.project.application.port.usecase.member

fun interface CheckIsOwnerUseCase {
    fun checkIsOwner(projectId: Long): Boolean
}