package ca.bccdcphl.service.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);
    // Only for use during early development.
    // TODO: Load this from the environment or config file.
    // TODO: Replace this simple token-based auth with real Oauth2/OIDC authentication
    private static final String READ_TOKEN = "secret";
    private static final String READ_WRITE_TOKEN = "supersecret";

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String providedToken = request.getHeader("Authorization");
        boolean tokenMatches = false;
        boolean bypassToken = false;

        if (providedToken == null) {
            if (request.getRequestURI().contains("/swagger-ui")) {
                bypassToken = true;
            }
            if (request.getRequestURI().contains("/api-docs")) {
                bypassToken = true;
            }
        } else {
            if (request.getMethod().equals("GET")) {

                tokenMatches = providedToken.equals("Bearer " + READ_TOKEN) || providedToken.equals("Bearer " + READ_WRITE_TOKEN);

            } else if (request.getMethod().equals("POST")) {
                tokenMatches = providedToken.equals("Bearer " + READ_WRITE_TOKEN);
            } else if (request.getMethod().equals("PATCH")) {
                tokenMatches = providedToken.equals("Bearer " + READ_WRITE_TOKEN);
            } else if (request.getMethod().equals("PUT")) {
                tokenMatches = providedToken.equals("Bearer " + READ_WRITE_TOKEN);
            } else if (request.getMethod().equals("DELETE")) {
                tokenMatches = providedToken.equals("Bearer " + READ_WRITE_TOKEN);
            }
        }

        if (tokenMatches || bypassToken) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}