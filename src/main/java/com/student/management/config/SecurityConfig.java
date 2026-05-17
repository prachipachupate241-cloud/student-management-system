package com.student.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // PASSWORD ENCODER

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // SECURITY

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                // CSRF OFF
                .csrf(csrf -> csrf.disable())

                // URL SECURITY
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC
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

                        // ADMIN + STUDENT
                        .anyRequest().hasAnyRole("ADMIN", "STUDENT")
                )

                // LOGIN
                .formLogin(form -> form

                        .loginPage("/login")

                        .usernameParameter("username")

                        .passwordParameter("password")

                        .defaultSuccessUrl("/students", true)

                        .permitAll()
                )

                // LOGOUT
                .logout(logout -> logout

                        .logoutSuccessUrl("/login")

                        .permitAll()
                );

        return http.build();
    }

    // USERS

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        // ADMIN

        UserDetails admin = User.builder()

                .username("admin")

                .password(encoder.encode("admin123"))

                .roles("ADMIN")

                .build();

        // STUDENT

        UserDetails student = User.builder()

                .username("student")

                .password(encoder.encode("student123"))

                .roles("STUDENT")

                .build();

        return new InMemoryUserDetailsManager(admin, student);
    }
}