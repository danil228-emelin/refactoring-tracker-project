package ru.ifmo.insys1.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,PATCH");
        resp.addHeader("Access-Control-Allow-Headers", "*");

        if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_OK);
            log.warn("Received OPTIONS request, return OK");

            return;
        }

        log.warn(
                "Received request with method: {}, URI: {}",
                request.getMethod(),
                request.getRequestURI()
        );

        filterChain.doFilter(request, servletResponse);
    }
}
