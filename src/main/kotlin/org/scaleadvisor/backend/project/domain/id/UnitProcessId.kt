package org.scaleadvisor.backend.project.domain.id

import org.scaleadvisor.backend.global.util.IdUtil

@JvmInline
value class UnitProcessId(val value: Long) {
    override fun toString(): String = value.toString()
    fun toLong(): Long = value
    companion object {
        fun newId() = UnitProcessId(IdUtil.generateId())
        fun from(id: String) = UnitProcessId(id.toLong())
        fun from(id: Long) = UnitProcessId(id)
    }
}