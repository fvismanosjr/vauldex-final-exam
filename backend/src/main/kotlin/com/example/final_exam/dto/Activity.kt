package com.example.final_exam.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class ActivityRequest(
    @field:Min(1)
    val userId: Long,

    @field:Min(1)
    val activityTypeId: Long,

    @field:NotBlank
    val description: String,
)

data class ActivityTypeResponse(
    val id: Long,
    val name: String
)

data class ActivityResponse(
    val id: Long,
    val user: UserResponse,
    val type: ActivityTypeResponse,
    val description: String,
    val createdAt: LocalDateTime
)