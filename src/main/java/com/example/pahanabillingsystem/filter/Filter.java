package com.example.pahanabillingsystem.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class Filter implements jakarta.servlet.Filter {

    private static final String ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ALLOW_METHODS = "Access-Control-Allow-Methods";
    private static final String ALLOW_HEADERS = "Access-Control-Allow-Headers";
    private static final String ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    private static final String EXPOSE_HEADERS = "Access-Control-Expose-Headers";

    private static final String ORIGIN = "http://localhost:5173"; // Specify frontend URL
    private static final String METHODS = "GET, POST, PUT, DELETE, OPTIONS";
    private static final String HEADERS = "Content-Type, Authorization, X-Requested-With";
    private static final String EXPOSED = "Content-Type, Authorization";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Set necessary CORS headers
        response.setHeader(ALLOW_ORIGIN, ORIGIN);
        response.setHeader(ALLOW_METHODS, METHODS);
        response.setHeader(ALLOW_HEADERS, HEADERS);
        response.setHeader(ALLOW_CREDENTIALS, "true");
        response.setHeader(EXPOSE_HEADERS, EXPOSED);

        // Handle preflight (OPTIONS) request
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Continue the filter chain for other requests
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
