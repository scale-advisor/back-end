package org.scaleadvisor.backend.project.domain.id

import org.scaleadvisor.backend.global.util.IdUtil

@JvmInline
value class FpWeightsId(val value: Long) {
    override fun toString(): String = value.toString()
    fun toLong(): Long = value
    companion object {
        fun newId() = FpWeightsId(IdUtil.generateId())
        fun of(id: String) = FpWeightsId(id.toLong())
        fun of(id: Long) = FpWeightsId(id)
    }
}
