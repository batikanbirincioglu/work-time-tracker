package com.wtt.commondependencies.filter;

import com.wtt.commondependencies.constants.Constants;
import com.wtt.commondependencies.model.Principal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String JWT_TOKEN = "JWT_TOKEN";
    private final JwtParser jwtParser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = request.getHeader(JWT_TOKEN);
            if (validateJwtToken(jwtParser, jwtToken)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(getPrincipal(jwtParser, jwtToken), jwtToken, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context: {}", ex);
        }
        filterChain.doFilter(request, response);
    }

    public static Principal getPrincipal(JwtParser parser, String jwtToken) {
        Claims claims = parser.parseClaimsJws(jwtToken).getBody();
        return Principal.builder()
                .id(claims.get(Constants.ID, Long.class))
                .name(claims.get(Constants.NAME, String.class))
                .email(claims.get(Constants.EMAIL, String.class))
                .manager(claims.get(Constants.MANAGER, Boolean.class))
                .build();
    }

    public static boolean validateJwtToken(JwtParser parser, String jwtToken) throws JwtException {
        if (StringUtils.hasText(jwtToken)) {
            parser.parseClaimsJws(jwtToken);
            return true;
        }
        return false;
    }

}