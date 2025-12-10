package com.example.final_exam.dto

import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val password: String,
)

data class RegisterRequest(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val email: String,

    @field:NotBlank
    val password: String,

    @field:NotBlank
    val confirmPassword: String,
) {
    @AssertTrue
    fun isPasswordMatching(): Boolean {
        return this.password == this.confirmPassword
    }
}

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
)