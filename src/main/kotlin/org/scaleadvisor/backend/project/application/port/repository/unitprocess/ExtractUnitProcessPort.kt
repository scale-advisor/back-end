package org.scaleadvisor.backend.project.application.port.repository.unitprocess

interface ExtractUnitProcessPort {
    operator fun invoke(prompt: String): String
}