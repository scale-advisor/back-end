package org.scaleadvisor.backend.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.scaleadvisor.backend.user.dto.ChangePwdRequest
import org.scaleadvisor.backend.user.dto.DeleteUserRequest
import org.scaleadvisor.backend.user.dto.UpdateNameRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "User", description = "사용자 관리 API")
@RequestMapping("users", produces = ["application/json;charset=utf-8"])
interface UserAPI {
    @Operation(
        summary = "이름 수정",
        description = "사용자 이름 변경"
    )
    @PatchMapping("/change-name")
    @ResponseStatus(HttpStatus.OK)
    fun updateUserName(@RequestBody request: UpdateNameRequest): ResponseEntity<String>

    @Operation(
        summary = "비밀번호 변경",
        description = "사용자 비밀번호 변경"
    )
    @PatchMapping("/change-pwd")
    @ResponseStatus(HttpStatus.OK)
    fun updatePassword(@RequestBody request: ChangePwdRequest): ResponseEntity<String>

    @Operation(
        summary = "회원 탈퇴",
        description = "사용자 정보 삭제"
    )
    @DeleteMapping("/delete-user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@CookieValue(value = "refreshToken", required = false) refreshToken: String?,
                   @RequestBody request: DeleteUserRequest,
                   response: HttpServletResponse)
}