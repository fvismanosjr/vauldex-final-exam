package com.example.final_exam.entity

import com.example.final_exam.dto.ActivityTypeResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "activity_types")
class ActivityType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "name")
    var name: String,

    @OneToMany(mappedBy = "activityType")
    var activities: MutableList<Activity> = mutableListOf()
) {
    fun toResponse(): ActivityTypeResponse {
        return ActivityTypeResponse(
            this.id,
            this.name
        )
    }
}