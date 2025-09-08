package com.rv.band_manager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Component implementation for handling a successful authentication.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     *
     * @param request HttpServletRequest that was used during the authentication attempt
     * @param response HttpServletResponse to be used to complete the authentication process
     * @param authentication AuthenticationException that was thrown during authentication
     * @throws IOException if an input or output exception occurs during the redirect
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CHILD"))) {

            // Invalidate the session to log out the committee_member immediately
            request.getSession().invalidate();

            // Redirect to the logout page or to a different location
            response.sendRedirect("/logout");
        } else {
            String redirectUrl = "/performance";

            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_DIRECTOR")) {
                    redirectUrl = "/director/committee";
                    break;
                }
                else if (authority.getAuthority().equals("ROLE_COMMITTEE_MEMBER")) {
                    redirectUrl = "/committee-member/performance";
                }
            }

            response.sendRedirect(redirectUrl);
        }


    }
}
