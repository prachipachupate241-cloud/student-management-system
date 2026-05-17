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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                // CSRF DISABLE
                .csrf(csrf -> csrf.disable())

                // URL SECURITY
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC PAGES
                        .requestMatchers(
                                "/",
                                "/login",
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        // ADMIN ONLY
                        .requestMatchers(
                                "/add/**",
                                "/edit/**",
                                "/delete/**",
                                "/save/**"
                        ).hasRole("ADMIN")

                        // ALL OTHER PAGES
                        .anyRequest().hasAnyRole("ADMIN", "STUDENT")
                )

                // LOGIN
                .formLogin(form -> form.permitAll())

                // LOGOUT
                .logout(logout -> logout

                        .logoutSuccessUrl("/login")

                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        // ADMIN USER
        UserDetails admin = User

                .withDefaultPasswordEncoder()

                .username("admin")

                .password("admin123")

                .roles("ADMIN")

                .build();

        // STUDENT USER
        UserDetails student = User

                .withDefaultPasswordEncoder()

                .username("student")

                .password("student123")

                .roles("STUDENT")

                .build();

        return new InMemoryUserDetailsManager(admin, student);
    }
}