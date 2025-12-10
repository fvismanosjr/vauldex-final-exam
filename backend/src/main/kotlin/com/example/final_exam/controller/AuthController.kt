package com.example.final_exam.controller

import com.example.final_exam.dto.LoginRequest
import com.example.final_exam.dto.RegisterRequest
import com.example.final_exam.service.UserService
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping("/auth")
class AuthController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody req: RegisterRequest
    ) = userService.register(req)

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody req: LoginRequest,
        response: HttpServletResponse
    ) = userService.login(req, response)

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse) = userService.logout(response)
}
