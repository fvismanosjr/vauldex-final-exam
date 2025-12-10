package com.example.final_exam.specification

import com.example.final_exam.entity.Activity
import com.example.final_exam.entity.ActivityType
import com.example.final_exam.entity.User
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate
import java.time.LocalDateTime

object ActivitySpecs {

    fun userEquals(userId: Long) = Specification<Activity> { root, _, cb ->
        cb.equal(root.join<Activity, User>("user").get<Long>("id"), userId)
    }

    fun activityTypeEquals(activityTypeId: Long) = Specification<Activity> { root, _, cb ->
        cb.equal(root.join<Activity, ActivityType>("activityType").get<Long>("id"), activityTypeId)
    }

    fun descriptionEquals(description: String) = Specification<Activity> { root, _, cb ->
        cb.equal(root.get<String>("description"), description)
    }

    fun createdAtEquals(date: LocalDate) = Specification<Activity> { root, _, cb ->
        val startOfDay: LocalDateTime = date.atStartOfDay()
        val endOfDay: LocalDateTime = date.plusDays(1).atStartOfDay()
        cb.between(root.get("createdAt"), startOfDay, endOfDay)
    }
}
