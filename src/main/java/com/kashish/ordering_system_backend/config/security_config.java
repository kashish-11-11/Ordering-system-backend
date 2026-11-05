package com.kashish.ordering_system_backend.config;

import com.kashish.ordering_system_backend.filter.jwt_filter;
import com.kashish.ordering_system_backend.service.my_user_detail_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class security_config {
    @Autowired
    private my_user_detail_service userDetailService_obj;
@Autowired
private jwt_filter jwt_filter_obj;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailService_obj);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurityobj) {
        httpSecurityobj.csrf(csrf -> csrf.disable());
        httpSecurityobj.authorizeHttpRequests(
                request -> request.requestMatchers("/register", "/login").permitAll().anyRequest().authenticated());
        // it means loginn and register anyone can access as if those APIs themselves
        // require authentication no one can login
        httpSecurityobj.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).addFilterBefore(jwt_filter_obj, UsernamePasswordAuthenticationFilter.class);
        return httpSecurityobj.build();
    }
}
