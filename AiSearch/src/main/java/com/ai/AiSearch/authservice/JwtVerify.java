package com.ai.AiSearch.authservice;

import com.ai.AiSearch.entity.RealUser;
import com.ai.AiSearch.userservice.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

public class JwtVerify extends OncePerRequestFilter {

    @Autowired
    Token token_class;
    @Autowired
    UserService userService;

    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String header = request.getHeader("Authorization");
            if (header == null || !(header.startsWith("Bearer"))) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = header.split("Bearer ")[1];
            Long id = token_class.getUserIdFromToken(token);
            //Exception Handling Required
            if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                RealUser user1 = userService.findById(id);
                if (user1.isActive() == false) {
                    throw new LockedException("User is Blocked");
                }
                UserDetailsImpl userDetails = new UserDetailsImpl(user1);


                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            filterChain.doFilter(request, response);
        }catch (Exception ex){
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
