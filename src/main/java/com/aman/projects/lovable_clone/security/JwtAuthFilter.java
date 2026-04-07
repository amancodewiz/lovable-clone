package com.aman.projects.lovable_clone.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AuthUtil authUtil;

        /*requestHeaderToken is : Bearer abcjcjavcscnvckabcdskvckacbasc
           .split("Bearer ") breaks it into parts: ["", "abcjcjavcscnvckabcdskvckacbasc"]
        [1] → takes the actual JWT token (second part)

        Authorization: "Bearer ", "abcjcjavcscnvckabcdskvckacbasc" (split the bearer and jwtToken[2nd part of string])
        Splits the header "Bearer <token>" by "Bearer " and extracts the actual JWT token (second part)*/

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        log.info("incoming request: {}", request.getRequestURI());

        String path = request.getRequestURI();

        // 🚨 skip auth endpoints
        if (path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String requestHeaderToken = request.getHeader("Authorization");

        if (requestHeaderToken == null || !requestHeaderToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = requestHeaderToken.substring(7);

        // 🚨 basic validation
        if (jwtToken.isBlank() || jwtToken.split("\\.").length != 3) {
            log.warn("Invalid JWT format");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            JwtUserPrincipal user = authUtil.verifyAccessToken(jwtToken);

            if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.authorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        } catch (Exception e) {
            log.error("JWT validation failed: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

}