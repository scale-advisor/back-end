package org.scaleadvisor.backend.project.domain.id

import org.scaleadvisor.backend.global.util.IdUtil

@JvmInline
value class CocomoScaleFactorId(val value: Long) {
    override fun toString(): String = value.toString()
    fun toLong(): Long = value
    companion object {
        fun newId() = CocomoScaleFactorId(IdUtil.generateId())
        fun of(id: String) = CocomoScaleFactorId(id.toLong())
        fun of(id: Long) = CocomoScaleFactorId(id)
    }
}