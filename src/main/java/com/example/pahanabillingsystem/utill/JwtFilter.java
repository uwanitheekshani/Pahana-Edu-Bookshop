package com.example.pahanabillingsystem.utill;

import io.jsonwebtoken.Claims;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String authHeader = req.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = JwtUtil.verifyToken(token);
                req.setAttribute("claims", claims);  // optional, use in controller if needed
                filterChain.doFilter(req, res);
            } catch (Exception e) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token.");
            }
        } else {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Authorization header.");
        }
    }
}
