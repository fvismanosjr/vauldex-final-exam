package com.example.final_exam.repository

import com.example.final_exam.entity.ActivityType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityTypeRepository : JpaRepository<ActivityType, Long>