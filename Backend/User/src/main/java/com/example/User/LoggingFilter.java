package com.example.User;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

@Component
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);



    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        logger.info("Request URL: {} | Method: {}", httpRequest.getRequestURL(), httpRequest.getMethod());

        chain.doFilter(request, response);

        logger.info("Response Status: {}", httpResponse.getStatus());
    }


    public void init(FilterConfig filterConfig) throws ServletException {}


    public void destroy() {}

    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}


