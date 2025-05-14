package org.scaleadvisor.backend.user.controller

import org.scaleadvisor.backend.user.dto.UpdateNameRequest
import org.scaleadvisor.backend.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @PreAuthorize("#userId == authentication.principal.getUserEntity().userId")
    @PatchMapping("/{userId}")
    fun updateUserName(@PathVariable userId: Long, @RequestBody request: UpdateNameRequest
    ): ResponseEntity<String> {
        userService.updateName(userId, request.newName)
        return ResponseEntity.ok().body("유저 이름이 변경되었습니다.")
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: Long): ResponseEntity<String> {
        userService.deleteUser(userId)
        return ResponseEntity.ok().body("$userId 유저 탈퇴가 완료 되었습니다.")
    }
}