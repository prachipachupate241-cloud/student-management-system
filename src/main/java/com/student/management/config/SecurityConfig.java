
package com.student.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/login",
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form

                        .loginPage("/login")

                        .defaultSuccessUrl(
                                "/students",
                                true
                        )

                        .permitAll()
                )

                .logout(logout -> logout

                        .logoutSuccessUrl("/login")

                        .permitAll()
                );

        return http.build();
    }
}

