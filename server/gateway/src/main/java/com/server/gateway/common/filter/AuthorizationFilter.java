package com.server.gateway.common.filter;

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
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
        super(Config.class);
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

            if (tokenEmail == null) {
                return handleUnAuthorized(exchange);
            }

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
