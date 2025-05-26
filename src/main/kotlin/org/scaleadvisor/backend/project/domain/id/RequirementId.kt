package org.scaleadvisor.backend.project.domain.id

import org.scaleadvisor.backend.global.util.IdUtil

@JvmInline
value class RequirementId(val value: Long) {
    override fun toString(): String = value.toString()
    fun toLong(): Long = value
    companion object {
        fun newId() = RequirementId(IdUtil.generateId())
        fun from(id: String) = RequirementId(id.toLong())
        fun from(id: Long) = RequirementId(id)
    }
}