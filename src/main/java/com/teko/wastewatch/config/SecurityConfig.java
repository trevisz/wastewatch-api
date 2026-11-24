package com.teko.wastewatch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/api/collection-points/**").hasAnyRole("USER","ADMIN")
                .requestMatchers("/api/pickups/**").hasAnyRole("USER","ADMIN")
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
        );
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService users(
            PasswordEncoder encoder,
            @Value("${wastewatch.security.admin-password}") String adminPassword,
            @Value("${wastewatch.security.user-password}") String userPassword) {
        UserDetails admin = User.withUsername("admin")
                .password(encoder.encode(adminPassword))
                .roles("ADMIN","USER")
                .build();
        UserDetails user = User.withUsername("user")
                .password(encoder.encode(userPassword))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
