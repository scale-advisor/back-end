package org.scaleadvisor.backend.project.application.port.repository

fun interface CreateUserProjectRepository {

    fun createUserProject(userId: Long, projectId: Long)

}
