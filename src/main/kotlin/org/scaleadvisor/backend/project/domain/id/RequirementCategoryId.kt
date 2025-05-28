package org.scaleadvisor.backend.project.domain.id

import org.scaleadvisor.backend.global.util.IdUtil

@JvmInline
value class RequirementCategoryId(val value: Long) {
    override fun toString(): String = value.toString()
    fun toLong(): Long = value
    companion object {
        fun newId() = RequirementCategoryId(IdUtil.generateId())
        fun from(id: String) = RequirementCategoryId(id.toLong())
        fun from(id: Long) = RequirementCategoryId(id)
    }
}