package org.scaleadvisor.backend.project.domain.id

import org.scaleadvisor.backend.global.util.IdUtil

@JvmInline
value class AdjustmentFactorId(val value: Long) {
    override fun toString(): String = value.toString()
    fun toLong(): Long = value
    companion object {
        fun newId() = AdjustmentFactorId(IdUtil.generateId())
        fun from(id: String) = AdjustmentFactorId(id.toLong())
        fun from(id: Long) = AdjustmentFactorId(id)
    }
}