package org.scaleadvisor.backend.project.infrastructure.ai

import org.scaleadvisor.backend.project.application.port.repository.unitprocess.ClassifyUnitProcessPort
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.domain.enum.FunctionType
import org.scaleadvisor.backend.project.domain.id.UnitProcessId
import org.scaleadvisor.backend.project.infrastructure.ai.client.FPClassifierClient
import org.scaleadvisor.backend.project.infrastructure.ai.client.FPClassifierClientRequest
import org.springframework.stereotype.Component

@Component
private class FPClassifierAdapter(
    private val fpClassifierClient: FPClassifierClient,
) : ClassifyUnitProcessPort {

    fun classify(unitProcessList: List<UnitProcess>): List<UnitProcess> {
        val results: List<UnitProcess>
        val requests: List<FPClassifierClientRequest>  = unitProcessList.map {
            FPClassifierClientRequest(
                it.name,
                it.id.toString(),
            )
        }
        val unitProcessIdMap = unitProcessList.associateBy { it.id.toString() }
        try {
            return fpClassifierClient.classify(requests).map {
                UnitProcess(
                    id = UnitProcessId.from(it.unitProcessId),
                    name = it.unitProcess,
                    functionType = FunctionType.valueOf(it.functionType),
                    isAmbiguous = unitProcessIdMap[it.unitProcessId]!!.isAmbiguous,
                )
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