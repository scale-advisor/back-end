package org.scaleadvisor.backend.project.application.port.usecase.member

fun interface CheckIsEditorUseCase {
    fun checkIsEditor(projectId: Long): Boolean
}