package com.deviation.blog.config.jwt;

import com.deviation.blog.common.ApiResultStatus;
import com.deviation.blog.common.BusinessException;
import com.deviation.blog.dto.UserTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${api.path.default}")
    private String API_URL_PREFIX;  // /v1/api

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String uri = request.getRequestURI();


        String[] equalsWith = {
                API_URL_PREFIX + "/users/sign-in",   // 로그인
                API_URL_PREFIX + "/users/resetpassword",   // 비밀번호 찾기
        };

        String[] startWith = {
                "/error"
        };

        String[] endWith = {
                ".html",".jpg",".png",".gif",".ico",".js",".css"
        };

        boolean equalsWithPass = Arrays.asList(equalsWith).contains(uri);
        boolean startWithPass = Arrays.stream(startWith).anyMatch(uri::startsWith);
        boolean endWithPass = Arrays.stream(endWith).anyMatch(uri::endsWith);

        if( equalsWithPass || startWithPass || endWithPass) {
            filterChain.doFilter(request, response);
            return;
        }


        log.debug("doFilterInternal");
        log.debug("[url]" + uri);

        try {

            String token = getToken(request);

            log.debug(token);

            if (token != null && jwtTokenProvider.validateJwtToken(token)) {

                UserTokenDto userInfo = UserTokenDto.builder()
                        .id(Long.valueOf(jwtTokenProvider.getBodyValue(token, "id")))
                        .email(jwtTokenProvider.getBodyValue(token, "email"))
                        .build();

                log.debug("[userInfo]" + userInfo.toString());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                request.setAttribute("Authentication", token);
                response.setHeader("Authentication", token);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // 토큰검증 예외처리
        } catch (BusinessException e) {

            log.error(e.getCode());
            log.error(e.getMessage());

            response.sendRedirect("/error/401?code="+ e.getCode());

            return;

        } catch (Exception e) {
            response.sendRedirect("/error/401?code="+ ApiResultStatus.TOKEN_INVALID.getCode());
        }


        log.info("JWT Token Authenticated");
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");
        if(authorization == null) return null;

        if (authorization.startsWith("Bearer ")) {
            return authorization.replace("Bearer ","");
        }

        return authorization;
    }

}
