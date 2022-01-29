package com.comeon.study.common.config.security;

import com.comeon.study.common.config.security.entrypoint.JwtAuthenticationEntrypoint;
import com.comeon.study.common.config.security.filter.JwtAuthenticationFilter;
import com.comeon.study.common.config.security.jwt.JwtTokenParser;
import com.comeon.study.common.config.security.jwt.JwtTokenValidator;
import com.comeon.study.common.config.security.service.JwtUserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenParser jwtTokenParser;

    private final JwtTokenValidator jwtTokenValidator;

    private final JwtUserDetailService jwtUserDetailService;

    private final ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/v1/join", "/api/v1/login", "/api/v1/refresh")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                    .disable()
                    .csrf()
                    .disable()
                    .cors()
                    .disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(new JwtAuthenticationEntrypoint(objectMapper))
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(
                            new JwtAuthenticationFilter(
                                    jwtTokenParser,
                                    jwtTokenValidator,
                                    jwtUserDetailService),
                            UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
