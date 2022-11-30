package com.order.ecommerce.config

import com.order.ecommerce.exception.CustomException
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*


private const val ROLE = "role"

@Component
class JwtTokenUtil {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Value("\${jwt.expirationDateInMs}")
    private var jwtExpirationInMs: Long = 0

    fun generateToken(userDetails: UserDetails): String {
        val claims: MutableMap<String, Any> = HashMap()
        val role = userDetails.authorities
        claims[ROLE] = role
        return doGenerateToken(claims, userDetails.username)
    }

    fun validateToken(authToken: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken)
            true
        } catch (e: SignatureException) {
            throw BadCredentialsException("Signature failed", e)
        } catch (e: MalformedJwtException) {
            throw BadCredentialsException("Malformed", e)
        } catch (e: UnsupportedJwtException) {
            throw BadCredentialsException("Unsupported exception", e)
        } catch (e: IllegalArgumentException) {
            throw BadCredentialsException("Invalid credentials", e)
        } catch (e: ExpiredJwtException) {
            throw CustomException("Token expired")
        }
    }

    fun getUsernameFromToken(token: String?): String? {
        val claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
        return claims.subject
    }

    fun getRolesFromToken(authToken: String?): List<SimpleGrantedAuthority?>? {
        val roles: List<SimpleGrantedAuthority?>?
        val claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).body
        val role = claims[ROLE]
        roles = listOf(SimpleGrantedAuthority(role.toString()))
        return roles
    }

    private fun doGenerateToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + jwtExpirationInMs))
            .signWith(SignatureAlgorithm.HS512, secret).compact()
    }
}
