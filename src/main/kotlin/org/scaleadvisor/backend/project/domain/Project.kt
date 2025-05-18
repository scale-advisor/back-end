package org.scaleadvisor.backend.project.domain

import java.time.OffsetDateTime

data class Project(
    val id: Long,
    var name: String,
    var description: String?,
    val createdAt: OffsetDateTime,
    var updatedAt: OffsetDateTime?
) {
    fun update(name: String, description: String?) {
        this.name = name
        this.description = description
        this.updatedAt = OffsetDateTime.now()
    }
}
