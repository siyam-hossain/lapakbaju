package com.lapakbaju.store.security.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            // Checks if the user has ADMIN role (handles both "ROLE_ADMIN" and "ADMIN")
            if (authority.getAuthority().equals("ROLE_ADMIN") || authority.getAuthority().equals("ADMIN")) {
                response.sendRedirect("/admin/analytics");
                return;
            }
        }

        // Regular clients/users go to home page
        response.sendRedirect("/");
    }
}