package com.operata.payment_service.filter;

import org.springframework.stereotype.Component;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    private final RestTemplate restTemplate;

    public AuthFilter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Bypass filter for webhooks
        String path = req.getRequestURI();
        if("/api/payments/webhook".equals(path)){
            chain.doFilter(request, response);
            return;
        }

        String authHeader = req.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        try {
            // Prepare the request to Service A
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Make the HTTP call to Service A's /validate endpoint
            ResponseEntity<String> authResponse = restTemplate.exchange(
                    "http://localhost:8081/api/auth/validate",
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (authResponse.getStatusCode() == HttpStatus.OK) {
                String userEmail = authResponse.getBody();
                req.setAttribute("userEmail", userEmail); // Safely pass email to the controller
                chain.doFilter(request, response); // Let the request proceed!
            } else {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Token validation failed");
        }
    }
}
