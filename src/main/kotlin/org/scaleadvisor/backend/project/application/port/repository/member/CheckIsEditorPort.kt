package org.scaleadvisor.backend.project.application.port.repository.member

fun interface CheckIsEditorPort {
    fun checkIsEditor(projectId: Long, userId: Long): Boolean
}