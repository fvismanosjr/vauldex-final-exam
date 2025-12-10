package com.example.final_exam.repository

import com.example.final_exam.entity.Activity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityRepository : JpaRepository<Activity, Long>{
    fun findAllByUserId(userId: Long, pageable: Pageable): Page<Activity>
}