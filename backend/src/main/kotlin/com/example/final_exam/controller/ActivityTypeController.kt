package com.example.final_exam.controller

import com.example.final_exam.repository.ActivityTypeRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/activity-types")
class ActivityTypeController(
   private val activityTypeRepository: ActivityTypeRepository
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll() = activityTypeRepository.findAll().map { it.toResponse() }
}