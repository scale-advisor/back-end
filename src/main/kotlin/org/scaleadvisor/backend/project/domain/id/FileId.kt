package org.scaleadvisor.backend.project.domain.id

import org.scaleadvisor.backend.global.util.IdUtil

@JvmInline
value class FileId(val value: Long) {
    override fun toString(): String = value.toString()
    fun toLong(): Long = value
    companion object {
        fun newId() = FileId(IdUtil.generateId())
        fun of(id: String) = FileId(id.toLong())
        fun of(id: Long) = FileId(id)
    }
}