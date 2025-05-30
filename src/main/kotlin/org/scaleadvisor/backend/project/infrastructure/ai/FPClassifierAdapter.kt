package org.scaleadvisor.backend.project.infrastructure.ai

import org.scaleadvisor.backend.project.application.port.repository.unitprocess.ClassifyUnitProcessPort
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.infrastructure.ai.client.FPClassifierClient
import org.scaleadvisor.backend.project.infrastructure.ai.client.FPClassifierClientRequest
import org.springframework.stereotype.Component

@Component
private class FPClassifierAdapter(
    private val fpClassifierClient: FPClassifierClient,
) : ClassifyUnitProcessPort {

    fun classify(unitProcessList: List<UnitProcess>): List<UnitProcess> {
        val requests: List<FPClassifierClientRequest>  = unitProcessList.map {
            FPClassifierClientRequest(
                it.name,
                it.id.toString(),
            )
        }
        val unitProcessIdMap = unitProcessList.associateBy { it.id.toString() }
        try {
            return fpClassifierClient.classify(requests).map { response ->
                val originalUnitProcess = unitProcessIdMap[response.unitProcessId]
                if (originalUnitProcess != null) {
                UnitProcess(
                    id = originalUnitProcess.id,
                    name = originalUnitProcess.name,
                    functionType = originalUnitProcess.functionType,
                    isAmbiguous = originalUnitProcess.isAmbiguous,
                )} else {
                    throw RuntimeException("응답에서 알 수 없는 unitProcessId를 받았습니다: ${response.unitProcessId}")
                }
            }
        } catch (ex: Exception) {
            // 로깅 또는 예외 처리
            throw RuntimeException("분류 API 호출 중 오류가 발생했습니다: ${ex.message}", ex)
        }
    }

    override fun invoke(unitProcessList: List<UnitProcess>): List<UnitProcess> {
        return this.classify(unitProcessList)
    }
}