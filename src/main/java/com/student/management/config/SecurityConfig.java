
package com.student.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 🔐 PASSWORD ENCODER

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // 👤 USERS

    @Bean
    public InMemoryUserDetailsManager userDetailsService(
            PasswordEncoder encoder
    ) {

        UserDetails admin = User.builder()

                .username("admin")

                .password(
                        encoder.encode("admin123")
                )

                .roles("ADMIN")

                .build();

        UserDetails user = User.builder()

                .username("user")

                .password(
                        encoder.encode("user123")
                )

                .roles("USER")

                .build();

        return new InMemoryUserDetailsManager(
                admin,
                user
        );
    }

    // 🔒 SECURITY

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                // ✅ CSRF

                .csrf(csrf -> csrf.disable())

                // ✅ AUTHORIZATION

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

                                "/students/add",
                                "/students/save",

                                "/students/edit/**",
                                "/students/delete/**",

                                "/attendance/save",

                                "/results/add/**",
                                "/results/save"

                        ).hasRole("ADMIN")

                        // ALL LOGGED USERS

                        .anyRequest().authenticated()
                )

                // ✅ LOGIN

                .formLogin(form -> form

                        .loginPage("/login")

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
}


