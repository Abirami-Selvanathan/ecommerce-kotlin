package com.order.ecommerce.config

import com.order.ecommerce.exception.CustomException
import com.order.ecommerce.service.OrderService
import io.jsonwebtoken.ExpiredJwtException
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val AUTHORIZATION = "Authorization"

private const val BEARER = "Bearer "

@Component
class CustomJwtAuthenticationFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    companion object {
        val log: Logger = getLogger(OrderService::class.java)
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            val jwtToken = extractJwtFromRequest(request)
            if (StringUtils.hasText(jwtToken) && jwtTokenUtil.validateToken(jwtToken)) {
                val userDetails: UserDetails = User(
                    jwtTokenUtil.getUsernameFromToken(jwtToken), "",
                    jwtTokenUtil.getRolesFromToken(jwtToken)
                )
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            } else {
                log.info("Unable to set security context")
            }
        } catch (e: ExpiredJwtException) {
            request.setAttribute("Exception", e)
            log.error("Token expired", e)
            throw CustomException("Token expired")
        } catch (e: BadCredentialsException) {
            request.setAttribute("Exception", e)
            log.error("Invalid credentials", e)
            throw CustomException("Invalid credentials")
        }
        chain.doFilter(request, response)
    }

    private fun extractJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTHORIZATION)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            bearerToken.substring(7, bearerToken.length)
        } else null
    }
}
