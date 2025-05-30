package org.scaleadvisor.backend.project.application.port.repository.unitprocess

interface ValidateUnitProcessPort {
    operator fun invoke(prompt: String): String
}