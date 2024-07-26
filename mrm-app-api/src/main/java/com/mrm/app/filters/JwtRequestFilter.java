package com.mrm.app.filters;

import com.mrm.app.constants.Headers;
import com.mrm.app.services.auth.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(Headers.AUTHORIZATION_HEADER);

        String jwt = null;
        String username = null;
        if (Headers.isBearerToken(authorizationHeader)) {
            jwt = Headers.extractJwt(authorizationHeader);
            username = extractUsername(jwt);
        }
        if (nonNull(username) && isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.isValidToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extractUsername(String jwt) {
        try {
            return jwtService.extractUsername(jwt);
        } catch (ExpiredJwtException exception) {
            return null;
        }
    }
}
