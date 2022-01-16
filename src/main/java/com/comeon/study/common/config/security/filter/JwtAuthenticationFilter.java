package com.comeon.study.common.config.security.filter;

import com.comeon.study.common.config.security.jwt.AuthHeader;
import com.comeon.study.common.config.security.jwt.JwtTokenParser;
import com.comeon.study.common.config.security.jwt.JwtTokenValidator;
import com.comeon.study.common.config.security.service.JwtUserDetailService;
import com.comeon.study.common.util.response.ApiResponseCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
        if (!jwtTokenValidator.validateAccessToken(tokenWithHeader)) {
            flushNonAuthorizedResult(response);
        }

        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(
                String.valueOf(jwtTokenParser.getAuthenticatedMemberId(tokenWithHeader)));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));

        filterChain.doFilter(request, response);
    }

    private void flushNonAuthorizedResult(HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            writer.print(ApiResponseCreator.createErrorResponse("유효하지 않은 토큰입니다."));
        }catch (IOException e) {
            log.error("writer을 가져오는데 실패했습니다. 이유 : {}", e.getMessage());
        }
    }
}
