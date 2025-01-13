package com.blaybus.appsolute.commons.interceptor;

import com.blaybus.appsolute.commons.annotation.Authenticated;
import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.commons.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Authenticated authenticated = handlerMethod.getMethodAnnotation(Authenticated.class);

            if(authenticated != null) {

                String token = jwtUtil.getBearerToken(request);

                if(token == null) {
                    throw new ApplicationException(
                            ErrorStatus.toErrorStatus("허가 되지 않은 사용자입니다.", 401, LocalDateTime.now())
                    );
                }

                try {
                    if(jwtUtil.isExpired(token)) {
                        throw new ApplicationException(
                                ErrorStatus.toErrorStatus("재로그인이 필요합니다.", 400, LocalDateTime.now())
                        );
                    }

                    request.setAttribute("id", jwtUtil.getId(token));
                } catch (Exception e) {
                    log.error("Invalid token: " + token, e);
                    throw new ApplicationException(
                            ErrorStatus.toErrorStatus("잘못된 토큰입니다.", 400, LocalDateTime.now())
                    );
                }
            }
        }
        return true;
    }
}