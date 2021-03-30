package com.rafhael.barabas.apiauth.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static java.util.Objects.nonNull;

@Service
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider provider;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider provider) {
        this.provider = provider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = provider.resolveToken((HttpServletRequest) request);

        if (nonNull(token) && provider.validateToken(token)) {
            Authentication auth = provider.getAuthentication(token);
            if (nonNull(auth)) {
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(auth);
            }
        }
        chain.doFilter(request, response);
    }
}
