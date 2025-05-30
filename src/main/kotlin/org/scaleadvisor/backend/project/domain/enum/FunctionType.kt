package org.scaleadvisor.backend.project.domain.enum

enum class FunctionType(
    val weight: Double
) {
    UNDEFINED(0.0),
    ILF(7.5),
    EIF(5.4),
    EI(4.0),
    EQ(5.2),
    EO(3.9);
}