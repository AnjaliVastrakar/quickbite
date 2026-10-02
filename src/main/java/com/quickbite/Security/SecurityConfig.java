
package com.quickbite.Security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomUserDetailsService customUserDetailsService) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

            // Enable CORS for React frontend
            .cors(cors -> {})

            // Disable CSRF because we are using JWT
            .csrf(csrf -> csrf.disable())

            // JWT application - no HTTP session
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Authorization rules
            .authorizeHttpRequests(auth -> auth

                // =========================
                // SWAGGER + ACTUATOR (public docs/health)
                // =========================
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/api-docs/**",
                    "/actuator/health",
                    "/actuator/info"
                ).permitAll()

                // =========================
                // AUTHENTICATION APIs
                // =========================
                .requestMatchers("/api/auth/**").permitAll()

                // =========================
                // USER REGISTRATION
                // =========================
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                // =========================
                // ADMIN APIs
                // =========================
                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")

                // =========================
                // ORDER APIs
                // =========================
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/orders/**"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/orders"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/orders/**"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/orders/**"
                ).authenticated()

                // =========================
                // RESTAURANT APIs
                // =========================

                // GET restaurants is public
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/restaurants/**"
                ).permitAll()

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/restaurants"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/restaurants/**"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/restaurants/**"
                ).authenticated()

                // =========================
                // MENU ITEM APIs (browse public, modify auth)
                // =========================
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/menu-items/**"
                ).permitAll()

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/menu-items"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/menu-items/**"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/menu-items/**"
                ).authenticated()

                // =========================
                // CART APIs
                // =========================
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/cart/**"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/cart"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/cart/**"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/cart/**"
                ).authenticated()

                // =========================
                // PAYMENT APIs
                // =========================
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/payments/**"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/payments"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/payments/**"
                ).authenticated()

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/payments/**"
                ).authenticated()

                // =========================
                // EVERYTHING ELSE
                // =========================
                .anyRequest().authenticated()
            )

            // Use custom UserDetailsService
            .userDetailsService(customUserDetailsService)

            // JWT filter runs before UsernamePasswordAuthenticationFilter
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    // Password encryption using BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // =========================
    // CORS CONFIGURATION
    // =========================
    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @org.springframework.beans.factory.annotation.Value("${app.cors.allowed-origins:http://localhost:5173}") String origins) {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
            Arrays.asList(origins.split(","))
        );

        configuration.setAllowedMethods(
            Arrays.asList(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
            )
        );

        configuration.setAllowedHeaders(
            Arrays.asList("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
