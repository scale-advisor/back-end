package org.scaleadvisor.backend.project.application.port.repository.member

fun interface CheckIsOwnerPort {
    fun checkIsOwner(projectId: Long, userId: Long): Boolean
}