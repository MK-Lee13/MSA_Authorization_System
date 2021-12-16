package com.server.user.common.config;

import com.server.user.common.interceptor.AdminAuthenticationInterceptor;
import com.server.user.common.interceptor.AuthenticationInterceptor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class WebConfiguration implements WebMvcConfigurer {
    private AuthenticationInterceptor authenticationInterceptor;
    private AdminAuthenticationInterceptor adminAuthenticationInterceptor;

    /**
     * 현재 사용하지 않은 인터셉터
     * MSA 적용 실패시 사용 예정
     * @param interceptorRegistry
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
//        interceptorRegistry.addInterceptor(authenticationInterceptor)
//                .addPathPatterns("/**");
//    }
}
