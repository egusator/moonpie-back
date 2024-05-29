package com.example.moonpie_back.core.config;

import com.example.moonpie_back.core.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private static final String AUTH_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(AUTH_HEADER);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Long id = jwtService.validateAndGetAuthorities(token);
        setPrincipal(id);
        filterChain.doFilter(request, response);
    }

    private void setPrincipal(Long id){
        JwtAuthentication jwtInfoToken = new JwtAuthentication(id);
        SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
    }
}

