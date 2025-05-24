package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.scaleadvisor.backend.api.response.SuccessResponse
import org.scaleadvisor.backend.project.api.response.GetFileResponse
import org.scaleadvisor.backend.project.domain.enum.FileType
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Project File", description = "Project File API")
@RequestMapping("projects/{projectId}/files", produces = ["application/json;charset=utf-8"])
interface ProjectFileAPI {

    @Operation(
        summary = "Project File 업로드",
        description = "RFP or 요구사항 파일 업로드",
        parameters  = [
            Parameter(
                name = "projectId",
                required = true,
                `in` = ParameterIn.PATH
            )
        ]
    )
    @PostMapping(value = [""], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun upload(
        @PathVariable projectId: Long,
        @RequestParam name: String,
        @RequestParam type: FileType,
        @RequestPart("file") file: MultipartFile
    )

    @Operation(
        summary = "Project File 정보 조회",
        description = "RFP or 요구사항 파일 메타 정보 조회",
        parameters  = [
            Parameter(
                name = "projectId",
                required = true,
                `in` = ParameterIn.PATH
            )
        ]
    )
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun find(
        @PathVariable projectId: Long,
        @RequestParam versionNumber: String
    ) : SuccessResponse<GetFileResponse>


    @Operation(
        summary = "Project File 다운로드",
        description = "RFP or 요구사항 파일 다운로드",
        parameters  = [
            Parameter(
                name = "projectId",
                required = true,
                `in` = ParameterIn.PATH
            )
        ]
    )
    @GetMapping("/download")
    @ResponseStatus(HttpStatus.OK)
    fun download(
        @PathVariable projectId: Long,
        @RequestParam versionNumber: String
    ) : ResponseEntity<ByteArrayResource>

}