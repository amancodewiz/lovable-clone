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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("incoming request: {}", request.getRequestURI());

        final String requestHeaderToken = request.getHeader("Authorization");
        if (requestHeaderToken != null && requestHeaderToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        /*requestHeaderToken is : Bearer abcjcjavcscnvckabcdskvckacbasc
           .split("Bearer ") breaks it into parts: ["", "abcjcjavcscnvckabcdskvckacbasc"]
        [1] → takes the actual JWT token (second part)

        Authorization: "Bearer ", "abcjcjavcscnvckabcdskvckacbasc" (split the bearer and jwtToken[2nd part of string])
        Splits the header "Bearer <token>" by "Bearer " and extracts the actual JWT token (second part)*/

        String jwtToken = requestHeaderToken.split("Bearer ")[1];

        JwtUserPrincipal user = authUtil.verifyAccessToken(jwtToken);

        if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.authorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
