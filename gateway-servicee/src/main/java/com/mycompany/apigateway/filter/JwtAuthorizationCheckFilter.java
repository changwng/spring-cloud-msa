package com.mycompany.apigateway.filter;

import com.mycompany.apigateway.utils.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtAuthorizationCheckFilter extends AbstractGatewayFilterFactory<JwtAuthorizationCheckFilter.Config> {

    private final JwtProvider jwtProvider;

    @Autowired
    public JwtAuthorizationCheckFilter(JwtProvider jwtProvider) {
        super(Config.class);
        this.jwtProvider = jwtProvider;
    }

    public static class Config{}

    @Override
    public GatewayFilter apply(Config config) {

        return (((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            HttpHeaders headers = request.getHeaders();
            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = headers.get(HttpHeaders.AUTHORIZATION).get(0);
            String token = authorizationHeader.replace("Bearer", "");

            if(jwtProvider.validateToken(token)) {
                log.info("This is a valid token -> isValid: {}", HttpHeaders.ALLOW);
                log.info("JwtAuthorizationCheckFilter Request -> IP : {}, URI : {}", request.getRemoteAddress().getAddress(), request.getURI());
            }

            String subject = jwtProvider.getUserId(token);
            if (subject.equals("feign")) return chain.filter(exchange);

            if (!jwtProvider.getRoles(token).contains("GROUP_USER")) {
                return onError(exchange, "권한 없음", HttpStatus.UNAUTHORIZED);
            }

            ServerHttpRequest newRequest = request.mutate()
                .header("member-id", subject)
                .build();

            return chain.filter(exchange.mutate().request(newRequest).build());
        }));

    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMsg, HttpStatus httpStatus) {
        log.error(errorMsg);

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }

}
