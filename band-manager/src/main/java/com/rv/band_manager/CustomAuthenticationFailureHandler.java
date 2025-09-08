package com.rv.band_manager;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Component implementation for handling a failed authentication.
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * Handles the authentication failure by processing the exception
     *
     * @param request HttpServletRequest that was used during the authentication attempt
     * @param response HttpServletResponse to be used to complete the authentication process
     * @param exception AuthenticationException that was thrown during authentication
     * @throws IOException if an input or output exception occurs during the redirect
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException {
        String errorMessage = exception.getMessage();

        if(exception instanceof BadCredentialsException){
            errorMessage = "Invalid email or password";
        }

        //Redirect to login page with error message as a query parameter
        response.sendRedirect("/login?error=" + errorMessage);
    }
}
