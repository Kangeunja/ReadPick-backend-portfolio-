package com.portfolio.ReadPick.config;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false); // 기존 세션만 가져옴 (새로 만들지 않음)

        // 세션이 없거나, 사용자 정보가 없는 경우
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return false; // Controller로 진입하지 않음
        }
        return true; // 세션이 존재하면 Controller로 요청 전달
    }
}
