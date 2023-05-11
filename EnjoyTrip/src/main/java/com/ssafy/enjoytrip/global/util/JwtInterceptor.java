package com.ssafy.enjoytrip.global.util;

import com.ssafy.enjoytrip.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            MemberDto member = jwtTokenProvider.authenticate(token);
            if (member != null) {
                return true;
            }
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonMessage = String.format("{\"status\": %d, \"message\": \"Unauthorized TOKEN\"}", HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(jsonMessage);
        return false;
    }
}
