package com.example.final_exam.service

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class UserConfig {

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService {
            // Replace with DB lookup (JPA repository)
            User.withUsername("admin")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build()
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
