package com.example.final_exam.service

import com.example.final_exam.dto.ActivityRequest
import com.example.final_exam.dto.ActivityResponse
import com.example.final_exam.entity.Activity
import com.example.final_exam.repository.ActivityRepository
import com.example.final_exam.repository.ActivityTypeRepository
import com.example.final_exam.repository.UserRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ActivityService(
    private val activityRepository: ActivityRepository,
    private val userRepository: UserRepository,
    private val activityTypeRepository: ActivityTypeRepository,
) {
    fun findAll(
        userId: Long,
        pageable: Pageable
    ) = activityRepository.findAllByUserId(userId, pageable).map { it.toResponse() }

    fun findById(
        userId: Long,
        activityId: Long,
    ): ActivityResponse = activityRepository.findById(activityId).map { it.toResponse() }.orElseThrow()

    fun save(
        userId: Long,
        request: ActivityRequest
    ): ActivityResponse {
        // check if user exists
        val user = userRepository.findById(userId).orElseThrow()

        // check if activity type exists
        val type = activityTypeRepository.findById(request.activityTypeId).orElseThrow()

        val activity = activityRepository.save(
            Activity(
                user = user,
                activityType = type,
                description = request.description
            )
        )

        return activity.toResponse()
    }

    fun update(
        userId: Long,
        activityId: Long,
        request: ActivityRequest
    ): ActivityResponse {
        // check if user exists
        val user = userRepository.findById(userId).orElseThrow()

        // check if activity exists
        val activity = activityRepository.findById(activityId).orElseThrow()

        // check if activity type exists
        val type = activityTypeRepository.findById(request.activityTypeId).orElseThrow()

        activity.user = user
        activity.activityType = type
        activity.description = request.description

        activityRepository.save(activity)

        return activity.toResponse()
    }

    fun delete(
        userId: Long,
        activityId: Long,
    ) {
        // check if user exists
        val user = userRepository.findById(userId).orElseThrow()

        // check if activity exists
        val activity = activityRepository.findById(activityId).orElseThrow()

        activityRepository.delete(activity)
    }
}