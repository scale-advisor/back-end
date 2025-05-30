package org.scaleadvisor.backend.user.controller

import jakarta.servlet.http.HttpServletResponse
import org.scaleadvisor.backend.api.UserAPI
import org.scaleadvisor.backend.user.dto.ChangePwdRequest
import org.scaleadvisor.backend.user.dto.DeleteUserRequest
import org.scaleadvisor.backend.user.dto.UpdateNameRequest
import org.scaleadvisor.backend.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) : UserAPI {
    override fun updateUserName(@RequestBody request: UpdateNameRequest): ResponseEntity<String> {
        userService.updateName(request)
        return ResponseEntity.ok().body("유저 이름이 변경되었습니다.")
    }

    override fun updatePassword(@RequestBody request: ChangePwdRequest): ResponseEntity<String> {
        userService.updatePwd(request)
        return ResponseEntity.ok().body("유저 비밀번호가 변경되었습니다.")
    }

    override fun deleteUser(@CookieValue(value = "refreshToken", required = false) refreshToken: String?,
                            @RequestBody request: DeleteUserRequest,
                            response: HttpServletResponse) {
        userService.deleteUser(request, response, refreshToken)
    }
}