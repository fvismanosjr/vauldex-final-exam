package com.example.final_exam.entity

import com.example.final_exam.dto.ActivityResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "activities")
class Activity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne
    @JoinColumn(name = "activity_type_id")
    var activityType: ActivityType,

    @Column(name = "description")
    var description: String,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun toResponse(): ActivityResponse {
        return ActivityResponse(
            this.id,
            this.user.toResponse(),
            this.activityType.toResponse(),
            this.description,
            this.createdAt
        )
    }
}