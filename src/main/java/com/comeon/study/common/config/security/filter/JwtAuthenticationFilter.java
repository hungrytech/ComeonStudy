package com.comeon.study.common.config.security.filter;

import com.comeon.study.common.config.security.jwt.AuthHeader;
import com.comeon.study.common.config.security.jwt.JwtTokenParser;
import com.comeon.study.common.config.security.jwt.JwtTokenValidator;
import com.comeon.study.common.config.security.service.JwtUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenParser jwtTokenParser;

    private final JwtTokenValidator jwtTokenValidator;

    private final JwtUserDetailService jwtUserDetailService;

    public JwtAuthenticationFilter(
            JwtTokenParser jwtTokenParser,
            JwtTokenValidator jwtTokenValidator,
            JwtUserDetailService jwtUserDetailService) {
        this.jwtTokenParser = jwtTokenParser;
        this.jwtTokenValidator = jwtTokenValidator;
        this.jwtUserDetailService = jwtUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String tokenWithHeader = request.getHeader(AuthHeader.AUTHENTICATION_HEADER);

        if (jwtTokenValidator.validateAccessToken(tokenWithHeader)) {
            UserDetails userDetails = jwtUserDetailService.loadUserByUsername(
                    jwtTokenParser.getAuthenticatedMemberIdFromAccessToken(tokenWithHeader));

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
        }

        filterChain.doFilter(request, response);
    }
}
