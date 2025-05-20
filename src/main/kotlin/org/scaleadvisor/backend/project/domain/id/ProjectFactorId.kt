package org.scaleadvisor.backend.project.domain.id

import org.scaleadvisor.backend.global.util.IdUtil

@JvmInline
value class ProjectFactorId(val value: Long) {
    override fun toString(): String = value.toString()
    fun toLong(): Long = value
    companion object {
        fun newId() = ProjectFactorId(IdUtil.generateId())
        fun of(id: String) = ProjectFactorId(id.toLong())
        fun of(id: Long) = ProjectFactorId(id)
    }
}