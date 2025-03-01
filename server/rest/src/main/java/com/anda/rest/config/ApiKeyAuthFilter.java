package com.anda.rest.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Collections;

@Component
public class ApiKeyAuthFilter extends GenericFilterBean {

    private final Environment env;

    public ApiKeyAuthFilter(Environment env) {
        this.env = env;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get API key from request header
        String apiKey = httpRequest.getHeader("X-API-KEY");

        // Retrieve the valid API key from application.properties (e.g., guest.api.key)
        String validApiKey = env.getProperty("guest.api.key");

        if (validApiKey != null && validApiKey.equals(apiKey)) {
            // API key is valid: create an authentication token for the guest user
            User guestUser = new User("guest-user", "", Collections.emptyList());
            PreAuthenticatedAuthenticationToken authentication =
                    new PreAuthenticatedAuthenticationToken(guestUser, null, guestUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Proceed with the request
            chain.doFilter(request, response);
        } else {
            // API key is missing or invalid: return 401 Unauthorized response
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Invalid or missing API Key");
        }
    }
}
