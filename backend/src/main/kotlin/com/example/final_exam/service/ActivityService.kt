package com.example.final_exam.service

import com.example.final_exam.dto.ActivityRequest
import com.example.final_exam.dto.ActivityResponse
import com.example.final_exam.entity.Activity
import com.example.final_exam.repository.ActivityRepository
import com.example.final_exam.repository.ActivityTypeRepository
import com.example.final_exam.repository.UserRepository
import com.example.final_exam.specification.ActivitySpecs
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ActivityService(
    private val activityRepository: ActivityRepository,
    private val userRepository: UserRepository,
    private val activityTypeRepository: ActivityTypeRepository,
) {
    fun findAll(
        userId: Long,
        filters: String?,
        pageable: Pageable
    ): Page<ActivityResponse> {

        val filterMap: Map<String, String> = filters
            ?.split("and")
            ?.mapNotNull { it.split("=").takeIf { p -> p.size == 2 } }
            ?.associate { it[0] to it[1] }
            .orEmpty()

        // map filter keys to their Specification functions
        val specs = filterMap.mapNotNull { (key, value) ->
            when (key) {
                "userId" -> ActivitySpecs.userEquals(value.toLong())
                "activityTypeId" -> ActivitySpecs.activityTypeEquals(value.toLong())
                "description" -> ActivitySpecs.descriptionEquals(value)
                "createdAt" -> ActivitySpecs.createdAtEquals(LocalDate.parse(value))
                else -> null
            }
        }

        val finalSpec = if (specs.isNotEmpty()) Specification.allOf(specs) else null

        return activityRepository.findAll(finalSpec, pageable).map { it.toResponse() }
    }

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
        userRepository.findById(userId).orElseThrow()

        // check if activity exists
        val activity = activityRepository.findById(activityId).orElseThrow()

        activityRepository.delete(activity)
    }
}