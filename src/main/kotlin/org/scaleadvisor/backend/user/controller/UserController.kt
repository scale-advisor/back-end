package org.scaleadvisor.backend.user.controller

import org.scaleadvisor.backend.user.service.UserService
import org.scaleadvisor.backend.user.dto.UserCreateRequest
import org.scaleadvisor.backend.user.dto.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): UserResponse {
        return userService.getUser(id)
    }

    @PostMapping
    fun createUser(@RequestBody request: UserCreateRequest): ResponseEntity<Long> {
        val userId = userService.createUser(request.username, request.email, request.password)
        return ResponseEntity.ok(userId)
    }
}

