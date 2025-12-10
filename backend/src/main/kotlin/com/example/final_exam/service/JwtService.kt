package com.example.final_exam.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.spec.SecretKeySpec
import jakarta.servlet.http.Cookie

@Service
class JwtService(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expiration}") private val expirationMs: Long
) {

    private fun getSigningKey() =
        SecretKeySpec(secret.toByteArray(), SignatureAlgorithm.HS256.jcaName)

    fun generateToken(username: String): String =
        Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationMs))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()

    fun extractUsername(token: String): String =
        Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .body
            .subject

    fun isTokenValid(token: String): Boolean =
        try {
            val claims: Claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .body
            !claims.expiration.before(Date())
        } catch (ex: Exception) {
            false
        }

    fun createJwtCookie(token: String): Cookie =
        Cookie("jwt", token).apply {
            isHttpOnly = true
            secure = true
            path = "/"
            maxAge = (expirationMs / 1000).toInt()
            setAttribute("SameSite", "None")
        }

    fun clearJwtCookie(): Cookie =
        Cookie("jwt", "").apply {
            isHttpOnly = true
            secure = true
            path = "/"
            maxAge = 0
            setAttribute("SameSite", "None")
        }

}
