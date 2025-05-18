package org.scaleadvisor.backend.project.domain

import org.scaleadvisor.backend.project.domain.id.ProjectId
import java.time.OffsetDateTime

data class Project(
    val id: ProjectId,
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
