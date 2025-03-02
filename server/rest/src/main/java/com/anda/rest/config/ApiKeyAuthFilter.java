package com.anda.rest.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        HttpServletRequest httpRequest  = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Retrieve API key from the request header "X-API-KEY"
        String apiKey = httpRequest.getHeader("X-API-KEY");

        // Retrieve valid API keys from application.properties
        String validGeneralApiKey = env.getProperty("user.api.key");
        String validGuestApiKey = env.getProperty("guest.api.key");

        if (validGuestApiKey != null && validGuestApiKey.equals(apiKey)) {
            // API key is valid for guest users: create authentication for guest
            User guestUser = new User("guest-user", "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_GUEST")));
            PreAuthenticatedAuthenticationToken authentication =
                    new PreAuthenticatedAuthenticationToken(guestUser, null, guestUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else if (validGeneralApiKey != null && validGeneralApiKey.equals(apiKey)) {
            // API key is valid for general users: create authentication for a general API user
            User apiUser = new User("api-user", "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
            PreAuthenticatedAuthenticationToken authentication =
                    new PreAuthenticatedAuthenticationToken(apiUser, null, apiUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            // API key is missing or invalid: return 401 Unauthorized response
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Invalid or missing API Key");
        }
    }
}
