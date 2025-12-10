package com.example.final_exam.config

import com.example.final_exam.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {

        val jwt = request.cookies?.firstOrNull { it.name == "jwt" }?.value

        if (jwt != null && jwtService.isTokenValid(jwt)) {
            val username = jwtService.extractUsername(jwt)
            val userDetails = userDetailsService.loadUserByUsername(username)

            val auth = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
            )

            SecurityContextHolder.getContext().authentication = auth
        }

        chain.doFilter(request, response)
    }
}
