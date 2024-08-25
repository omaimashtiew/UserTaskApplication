package com.task.TaskManagementApplication;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Retrieve the loginType parameter
        String loginType = request.getParameter("loginType");

        if ("admin".equals(loginType)) {
            response.sendRedirect("/tasks");
        } else if ("user".equals(loginType)) {
            response.sendRedirect("/tasks2");
        } else {
            response.sendRedirect("/login");
        }
    }
}