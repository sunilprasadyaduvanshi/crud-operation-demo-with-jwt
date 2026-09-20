package com.crud.filter;

import com.crud.config.AuthAccessDetailService;
import com.crud.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AuthAccessDetailService authAccessDetailService;

    public JwtAuthFilter(JwtService jwtService, AuthAccessDetailService authAccessDetailService) {
        this.jwtService = jwtService;
        this.authAccessDetailService = authAccessDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.info("START:: doFilterInternal() for request " + request.getRequestURI());
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7).trim();
            logger.info("Extracted token: " + token);
            try {
                username = jwtService.extractUsername(token);
                logger.info("Extracted username: " + username);
            } catch (Exception ex) {
                logger.warn("Invalid JWT token supplied for request " + request.getRequestURI(), ex);
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = authAccessDetailService.loadUserByUsername(username);
                logger.info("Loaded userDetails: " + userDetails);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.info("User authenticated successfully: " + username);
                }
            } catch (Exception ex) {
                logger.warn("Unable to authenticate user " + username + " from JWT token", ex);
            }
        }
        logger.info("Proceeding with filter chain for request " + request.getRequestURI());
        filterChain.doFilter(request, response);
    }
}
