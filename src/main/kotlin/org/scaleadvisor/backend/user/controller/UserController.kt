package org.scaleadvisor.backend.user.controller

import org.scaleadvisor.backend.user.dto.ChangePwdRequest
import org.scaleadvisor.backend.user.dto.UpdateNameRequest
import org.scaleadvisor.backend.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @PatchMapping("/change-name")
    fun updateUserName(@RequestBody request: UpdateNameRequest): ResponseEntity<String> {
        userService.updateName(request)
        return ResponseEntity.ok().body("유저 이름이 변경되었습니다.")
    }

    @PatchMapping("/change-pwd")
    fun updatePassword(@RequestBody request: ChangePwdRequest): ResponseEntity<String> {
        userService.updatePwd(request)
        return ResponseEntity.ok().body("유저 비밀번호가 변경되었습니다.")
    }

    @DeleteMapping("/delete-user")
    fun deleteUser(): ResponseEntity<String> {
        userService.deleteUser()
        return ResponseEntity.ok().body("유저 탈퇴가 완료 되었습니다.")
    }
}