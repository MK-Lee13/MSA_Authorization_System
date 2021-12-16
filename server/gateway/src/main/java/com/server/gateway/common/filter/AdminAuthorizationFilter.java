package com.server.gateway.common.filter;

import com.server.gateway.common.api.UserServiceFeignClient;
import com.server.gateway.common.dto.UserResponseDto;
import com.server.gateway.common.security.JwtTokenProvider;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
public class AdminAuthorizationFilter extends AbstractGatewayFilterFactory<AdminAuthorizationFilter.Config> {
//    private UserServiceFeignClient userServiceFeignClient;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AdminAuthorizationFilter(
//            UserServiceFeignClient userServiceFeignClient,
            JwtTokenProvider jwtTokenProvider
    ) {
        super(Config.class);
//        this.userServiceFeignClient = userServiceFeignClient;
        this.jwtTokenProvider = jwtTokenProvider;

    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            /**
             * token 헤더 존재 여부 검사
             */
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return handleUnAuthorized(exchange); // 401 Error
            }

            List<String> accessTokens = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
            String accessToken = Objects.requireNonNull(accessTokens).get(0);

            /**
             * token 사용자 검색
             */
            String tokenEmail = jwtTokenProvider.getUserEmail(accessToken);
            // TODO : 추후에 Null 제거합시다
            UserResponseDto userResponseDto = null;
//            try {
//                userResponseDto = userServiceFeignClient.getUser(tokenEmail);
//            } catch(Exception e) {
//                // TODO : 추후에 Exception 정의해서 만들어야합니다
//                return handleUnAuthorized(exchange); // 401 Error
//            }
//
//            /**
//             * token 사용자 검증
//             */
//            if (userResponseDto == null) {
//                // TODO : 추후에 Exception 정의해서 만들어야합니다
//                return handleUnAuthorized(exchange); // 401 Error
//            }
//
//            /**
//             * token 사용자 권한 검증
//             */
//            if (userResponseDto.getUserRole().equals("ROLE_ADMIN") == false) {
//                // TODO : 추후에 Exception 정의해서 만들어야합니다
//                return handleUnAuthorized(exchange); // 401 Error
//            }

            return chain.filter(exchange);
        }));
    }

    private Mono<Void> handleUnAuthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    public static class Config {
    }
}
