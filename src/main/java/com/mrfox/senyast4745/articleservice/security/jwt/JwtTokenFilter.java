package com.mrfox.senyast4745.articleservice.security.jwt;

import com.google.gson.Gson;
import com.mrfox.senyast4745.articleservice.forms.ExceptionModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException, InvalidJwtAuthenticationException {

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);

                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }catch (InvalidJwtAuthenticationException e){
            ExceptionModel ex = new ExceptionModel(403, "Forbidden",
                    e.getMessage(), ((HttpServletRequest) req).getRequestURI());
            /*Gson gson = new Gson();
            String json = gson.toJson(ex);
            try {
                res.getWriter().append(json);
            } catch (Exception ignore){}*/
        }
        filterChain.doFilter(req, res);
    }
}
