package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.controller.response.fpweights.GetFpWeightsResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "FP Weights", description = "가중치 조회 API")
@RequestMapping("fp-weights", produces = ["application/json;charset=utf-8"])
interface FpWeightsAPI {

//    @Operation(
//        summary = "FP 가중치 생성",
//        description = "프로젝트의 기능 유형별 가중치 첫 설정.\n\n" +
//                "조회 API를 통해 조회된 isGovStandard가 true이고,\n\n" +
//                "사용자가 값을 갱신하여 저장 시 이 API 호출."
//    )
//    @PostMapping("/{projectId}/fp-weights")
//    @ResponseStatus(HttpStatus.OK)
//    fun create(
//        @PathVariable projectId: Long,
//        @RequestBody request: CreateFpWeightsRequest
//    ): SuccessResponse<CreateFpWeightsResponse>
//
//    @Operation(
//        summary = "FP 가중치 조회",
//        description = "프로젝트의 기능 유형별 가중치 설정값 조회.\n\n" +
//                "없으면 정부 표준값 사용."
//    )
//    @GetMapping("/{projectId}/fp-weights")
//    @ResponseStatus(HttpStatus.OK)
//    fun find(@PathVariable projectId: Long): SuccessResponse<FindFpWeightsResponse>
//
//    @Operation(
//        summary = "FP 가중치 수정",
//        description = "프로젝트의 기능 유형별 가중치 갱신"
//    )
//    @PutMapping("/{projectId}/fp-weights")
//    @ResponseStatus(HttpStatus.OK)
//    fun update(
//        @PathVariable projectId: Long,
//        @RequestBody request: UpdateFpWeightsRequest
//    ): SuccessResponse<UpdateFpWeightsResponse>
//
//    @Operation(
//        summary = "FP 가중치 삭제 및 초기화",
//        description = "프로젝트의 기능 유형별 가중치 삭제 및 초기화"
//    )
//    @DeleteMapping("/{projectId}/fp-weights")
//    @ResponseStatus(HttpStatus.OK)
//    fun delete(@PathVariable projectId: Long)


    @Operation(
        summary = "FP 가중치 정보 조회",
        description = "ILF, EIF, EI, EO, EQ에 대한 표준 가중치 값 조회"
    )
    @GetMapping("")
    fun get(): SuccessResponse<GetFpWeightsResponse>
}