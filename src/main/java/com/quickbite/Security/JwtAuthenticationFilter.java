
package com.quickbite.Security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService) {

        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Check whether Authorization header exists
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            // Check whether JWT is valid
            if (jwtService.isTokenValid(token)) {

                String email = jwtService.extractEmail(token);
                String role = jwtService.extractRole(token);

                System.out.println("JWT EMAIL = " + email);
                System.out.println("JWT ROLE = " + role);

                // Load user from database
                UserDetails userDetails =
                        customUserDetailsService.loadUserByUsername(email);

                /*
                 * Make sure role is not null or empty
                 */
                if (role != null && !role.isBlank()) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    List.of(
                                            new SimpleGrantedAuthority(role)
                                    )
                            );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);

                    System.out.println(
                            "AUTHORITIES = "
                            + authentication.getAuthorities()
                    );
                }
            }
        }

        // Continue the request
        filterChain.doFilter(request, response);
    }
}

