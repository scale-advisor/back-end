package org.scaleadvisor.backend.project.domain.enum

enum class AdjustmentFactorType(private val valuesByLevel: Array<String>) {
    INTEGRATION_COMPLEXITY(arrayOf("0.88", "0.94", "1.00", "1.06", "1.12")),
    PERFORMANCE(arrayOf("0.91", "0.95", "1.00", "1.05", "1.09")),
    OPERATIONAL_COMPATIBILITY(arrayOf("0.94", "1.00", "1.06", "1.13", "1.19")),
    SECURITY(arrayOf("0.97", "1.00", "1.03", "1.06", "1.08"));

    fun valueFor(level: Int): String =
        valuesByLevel.getOrNull(level-1)
            ?: throw IllegalArgumentException("Unsupported level: $level (0‒${valuesByLevel.lastIndex})")
}