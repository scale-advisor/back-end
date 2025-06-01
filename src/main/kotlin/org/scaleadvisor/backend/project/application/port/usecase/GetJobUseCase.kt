package org.scaleadvisor.backend.project.application.port.usecase

import org.scaleadvisor.backend.global.job.AnalysisJob

interface GetJobUseCase {

    operator fun invoke(jobId: String): AnalysisJob?
}