package com.example.final_exam.controller

import com.example.final_exam.dto.ActivityRequest
import com.example.final_exam.service.ActivityService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/users/{userId}/activities")
class ActivityController(
    private val activityService: ActivityService
) {
   @GetMapping
   @ResponseStatus(HttpStatus.OK)
    fun findAll(
        @PathVariable userId: Long,
        @RequestParam filters: String?,
        @PageableDefault(
            page = 0,
            size = 10,
            sort = ["id"],
            direction = Sort.Direction.ASC
        ) pageable: Pageable
    ) = activityService.findAll(userId, filters, pageable)

    @GetMapping("/{activityId}")
    @ResponseStatus(HttpStatus.OK)
    fun findById(
        @PathVariable userId: Long,
        @PathVariable activityId: Long,
    ) = activityService.findById(userId, activityId)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun store(
        @PathVariable userId: Long,
        @Valid @RequestBody request: ActivityRequest
    ) = activityService.save(userId, request)

    @PutMapping("/{activityId}")
    @ResponseStatus(HttpStatus.OK)
    fun edit(
        @PathVariable userId: Long,
        @PathVariable activityId: Long,
        @Valid @RequestBody request: ActivityRequest
    ) = activityService.update(userId, activityId, request)

    @DeleteMapping("/{activityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun destroy(
        @PathVariable userId: Long,
        @PathVariable activityId: Long,
    ) = activityService.delete(userId, activityId)
}