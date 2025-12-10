package com.example.final_exam.entity

import com.example.final_exam.dto.UserResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "email")
    var email: String,

    @Column(name = "name")
    var name: String,

    @Column(name = "password")
    var password: String,
) {
    fun toResponse(): UserResponse {
        return UserResponse(
            this.id,
            this.name,
            this.email,
        )
    }
}