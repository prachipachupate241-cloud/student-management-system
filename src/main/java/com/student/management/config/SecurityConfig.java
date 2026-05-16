package com.student.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // ✅ SECURITY

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                // ✅ CSRF OFF

                .csrf(csrf -> csrf.disable())

                // ✅ URL ACCESS

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/login",
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )

                // ✅ LOGIN

                .formLogin(form -> form

                        .defaultSuccessUrl(
                                "/students",
                                true
                        )

                        .permitAll()
                )

                // ✅ LOGOUT

                .logout(logout -> logout

                        .logoutSuccessUrl("/login")

                        .permitAll()
                );

        return http.build();
    }

    // ✅ USERNAME PASSWORD

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User

                .withDefaultPasswordEncoder()

                .username("admin")

                .password("admin123")

                .roles("ADMIN")

                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}