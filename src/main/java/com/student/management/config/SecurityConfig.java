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

                .csrf(csrf -> csrf.disable())

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
                                "/students/add",
                                "/students/edit/**",
                                "/students/delete/**",
                                "/attendance/add",
                                "/results/add"
                        ).hasRole("ADMIN")

                        // STUDENT + ADMIN
                        .requestMatchers(
                                "/students",
                                "/attendance",
                                "/results"
                        ).hasAnyRole("ADMIN", "STUDENT")

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form

                        .loginPage("/login")

                        .defaultSuccessUrl("/students", true)

                        .permitAll()
                )

                .logout(logout -> logout

                        .logoutSuccessUrl("/login")

                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        // ADMIN
        UserDetails admin = User
                .withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();

        // STUDENT
        UserDetails student = User
                .withDefaultPasswordEncoder()
                .username("student")
                .password("student123")
                .roles("STUDENT")
                .build();

        return new InMemoryUserDetailsManager(admin, student);
    }
}