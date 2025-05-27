package org.scaleadvisor.backend.project.domain.enum

enum class AdjustmentFactorType(
    val koreanName: String,
    private val levels: Array<LevelInfo>
) {
    INTEGRATION_COMPLEXITY(
        "연계복잡성",
        arrayOf(
            LevelInfo("0.88", "타 기관 연계 없음"),
            LevelInfo("0.94", "1~2개의 타 기관 연계"),
            LevelInfo("1.00", "3~5개의 타 기관 연계"),
            LevelInfo("1.06", "6~10개의 타 기관 연계"),
            LevelInfo("1.12", "10개 초과의 타 기관 연계")
        )
    ),

    PERFORMANCE(
        "성능 요구수준",
        arrayOf(
            LevelInfo("0.91", "응답성능에 대한 특별한 요구사항이 없다."),
            LevelInfo("0.95", "응답성능에 대한 요구사항이 있으나 특별한 조치가 필요하지는 않다."),
            LevelInfo("1.00", "응답시간이나 처리율이 피크타임(peak time)에 중요하며, 처리 시한이 명시되어 있다."),
            LevelInfo("1.05", "응답시간이나 처리율이 모든 업무시간에 중요하며, 처리 시한이 명시되어 있다."),
            LevelInfo("1.09", "응답성능 요구수준이 엄격하여, 설계, 개발 또는 구현 단계에서 성능 분석도구 사용이 필요하다.")
        )
    ),

    OPERATIONAL_COMPATIBILITY(
        "운영환경 호환성",
        arrayOf(
            LevelInfo("0.94", "운영환경 호환성에 대한 요구사항이 없다."),
            LevelInfo("1.00", "운영환경 호환성에 대한 요구사항이 있으며, 동일 하드웨어 및 소프트웨어 환경에서 운영되도록 설계된다."),
            LevelInfo("1.06", "운영환경 호환성에 대한 요구사항이 있으며, 유사 하드웨어 및 소프트웨어 환경에서 운영되도록 설계된다."),
            LevelInfo("1.13", "운영환경 호환성에 대한 요구사항이 있으며, 이질적인 하드웨어 및 소프트웨어 환경에서 운영되도록 설계된다."),
            LevelInfo("1.19", "항목 4에 더하여 일반적 산출물 이외에 장소에서 원활한 운영을 보장하기 위한 운영 절차의 문서화와 사전 모의훈련이 요구된다.")
        )
    ),

    SECURITY(
        "보안성 요구수준",
        arrayOf(
            LevelInfo("0.97", "암호화, 웹취약점 점검, 시큐어코딩, 개인정보보호 등 1가지 보안 요구사항이 포함되어 있다."),
            LevelInfo("1.00", "2가지 요구사항이 포함되어 있다."),
            LevelInfo("1.03", "3가지 요구사항이 포함되어 있다."),
            LevelInfo("1.06", "4가지 항목이 모두 포함되어 있다."),
            LevelInfo("1.08", "5가지 이상의 보안 요구사항이 포함되어 있다.")
        )
    );

    data class LevelInfo(
        val value: String,
        val description: String
    )

    fun info(level: Int): LevelInfo =
        levels.getOrNull(level - 1)
            ?: throw IllegalArgumentException(
                "Unsupported level: $level (valid 1‒${levels.size})"
            )

    fun valueFor(level: Int): String = info(level).value

    fun descFor(level: Int): String = info(level).description
}