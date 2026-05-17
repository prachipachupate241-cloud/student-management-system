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

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

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

                        // ALL USERS
                        .anyRequest().hasAnyRole("ADMIN", "STUDENT")
                )

                .formLogin(form -> form.permitAll())

                .logout(logout -> logout

                        .logoutSuccessUrl("/login")

                        .permitAll()
                );

        return http.build();
    }

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