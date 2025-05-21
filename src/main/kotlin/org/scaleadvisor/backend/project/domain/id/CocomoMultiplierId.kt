package org.scaleadvisor.backend.project.domain.id

import org.scaleadvisor.backend.global.util.IdUtil

@JvmInline
value class CocomoMultiplierId(val value: Long) {
    override fun toString(): String = value.toString()
    fun toLong(): Long = value
    companion object {
        fun newId() = CocomoMultiplierId(IdUtil.generateId())
        fun of(id: String) = CocomoMultiplierId(id.toLong())
        fun of(id: Long) = CocomoMultiplierId(id)
    }
}
