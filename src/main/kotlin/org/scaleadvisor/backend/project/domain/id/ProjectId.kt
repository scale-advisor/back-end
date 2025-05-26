package org.scaleadvisor.backend.project.domain.id

import org.scaleadvisor.backend.global.util.IdUtil

@JvmInline
value class ProjectId(val value: Long) {
    override fun toString(): String = value.toString()
    fun toLong(): Long = value
    companion object {
        fun newId() = ProjectId(IdUtil.generateId())
        fun from(id: String) = ProjectId(id.toLong())
        fun from(id: Long) = ProjectId(id)
    }
}