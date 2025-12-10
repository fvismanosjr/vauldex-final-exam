package com.example.final_exam.service

import com.example.final_exam.dto.LoginRequest
import com.example.final_exam.dto.RegisterRequest
import com.example.final_exam.dto.UserResponse
import com.example.final_exam.entity.User
import com.example.final_exam.repository.UserRepository
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) {

    fun register(request: RegisterRequest): String {
        userRepository
            .findByEmail(request.email)
            ?.let { throw Exception("email already taken") }

        val newUser = User(
            email = request.email,
            name = request.name,
            password = passwordEncoder.encode(request.password)
        )

        userRepository.save(newUser)
        return "User registered successfully"
    }

    fun login(request: LoginRequest, response: HttpServletResponse): UserResponse {
        val user = userRepository
                    .findByEmail(request.email)
                    ?: throw Exception("user with this email not found")

        val auth = UsernamePasswordAuthenticationToken(request.email, request.password)
        authenticationManager.authenticate(auth)

        val token = jwtService.generateToken(request.email)
        response.addCookie(jwtService.createJwtCookie(token))

        return user.toResponse()
    }

    fun logout(response: HttpServletResponse): String {
        response.addCookie(jwtService.clearJwtCookie())
        return "Logged out successfully"
    }
}
