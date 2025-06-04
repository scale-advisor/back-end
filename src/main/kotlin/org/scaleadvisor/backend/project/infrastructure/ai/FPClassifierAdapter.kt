package org.scaleadvisor.backend.project.infrastructure.ai

import org.scaleadvisor.backend.project.application.port.repository.unitprocess.ClassifyUnitProcessPort
import org.scaleadvisor.backend.project.domain.UnitProcess
import org.scaleadvisor.backend.project.domain.enum.FunctionType
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
            val classifiedResults = mutableListOf<UnitProcess>()
            requests.chunked(100).forEach { chunkedRequests ->
                val responses = fpClassifierClient.classify(chunkedRequests)

                responses.forEach { response ->
                    val originalUnitProcess = unitProcessIdMap[response.unitProcessId]
                    if (originalUnitProcess != null) {
                        classifiedResults.add(
                            UnitProcess(
                                id = originalUnitProcess.id,
                                projectVersionId = originalUnitProcess.projectVersionId,
                                name = originalUnitProcess.name,
                                functionType = FunctionType.valueOf(response.functionType),
                                isAmbiguous = originalUnitProcess.isAmbiguous,
                            )
                        )
                    } else {
                        throw RuntimeException(
                            "응답에서 알 수 없는 unitProcessId를 받았습니다: ${response.unitProcessId}"
                        )
                    }
                }
            }

            return classifiedResults

        } catch (ex: Exception) {
            throw RuntimeException("분류 API 호출 중 오류가 발생했습니다: ${ex.message}", ex)
        }
    }

    override fun invoke(unitProcessList: List<UnitProcess>): List<UnitProcess> {
        return this.classify(unitProcessList)
    }

}