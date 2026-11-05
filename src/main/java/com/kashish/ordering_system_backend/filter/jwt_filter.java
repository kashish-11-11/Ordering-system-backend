
package com.kashish.ordering_system_backend.filter;

import com.kashish.ordering_system_backend.service.jwt_service;
import com.kashish.ordering_system_backend.service.my_user_detail_service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class jwt_filter extends OncePerRequestFilter {

    @Autowired
    private jwt_service jwt_service_obj;

    @Autowired
    private my_user_detail_service user_detail_service_obj;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request_obj,
            HttpServletResponse response_obj,
            FilterChain filterChain__obj
    ) throws ServletException, IOException {

        String auth_header = request_obj.getHeader("Authorization");

        if (auth_header == null || !auth_header.startsWith("Bearer ")) {
            filterChain__obj.doFilter(request_obj, response_obj);
            return;
        }

        String token = auth_header.substring(7);

        try {
            String email = jwt_service_obj.extract_email(token);

            if (email != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails_obj =
                        user_detail_service_obj.loadUserByUsername(email);

                if (jwt_service_obj.validate_token(
                        token, userDetails_obj.getUsername())) {

                    UsernamePasswordAuthenticationToken authenticationToken_obj =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails_obj,
                                    null,
                                    userDetails_obj.getAuthorities()
                            );

                    authenticationToken_obj.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request_obj)
                    );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authenticationToken_obj);
                }
            }

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        filterChain__obj.doFilter(request_obj, response_obj);
    }
}
