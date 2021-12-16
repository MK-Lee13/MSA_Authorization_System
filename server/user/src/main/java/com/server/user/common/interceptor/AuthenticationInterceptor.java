package com.server.user.common.interceptor;

import com.server.user.common.security.JwtTokenProvider;
import com.server.user.users.domain.User;
import com.server.user.users.repository.UserRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        /**
         * token 포맷 및 만료기간 검증
         */
        if (jwtTokenProvider.validateToken(token) == false) {
            // TODO : 추후에 Exception 정의해서 만들어야합니다
            return false;
        }

        /**
         * token 사용자 검색
         */
        String tokenEmail = jwtTokenProvider.getUserEmail(token);
        Optional<User> users = userRepository.findByEmail(tokenEmail);

        /**
         * token 사용자 검증
         */
        if (users.isPresent() == false) {
            // TODO : 추후에 Exception 정의해서 만들어야합니다
            return false;
        }

        return true;
    }
}
