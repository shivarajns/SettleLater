package com.SettleLater.Backend.Common.Configuration;

import com.SettleLater.Backend.Security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session-> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/register",
                                "/login",
                                "/test/public"
                        )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                );
        return httpSecurity.build();
    }


}
