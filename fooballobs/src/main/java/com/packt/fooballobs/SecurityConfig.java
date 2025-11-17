package com.packt.fooballobs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/actuator/**")
                                .hasRole("ADMIN")
                                .anyRequest().authenticated())
                .oauth2ResourceServer(aouth2 -> aouth2.jwt(Customizer.withDefaults()))
                .build();

    }
}
