package org.scaleadvisor.backend.project.domain

import java.time.LocalDateTime

data class Project(
    val id: Long,
    var name: String,
    var description: String?,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime?
) {
    fun update(name: String, description: String?) {
        this.name = name
        this.description = description
        this.updatedAt = LocalDateTime.now()
    }
}
