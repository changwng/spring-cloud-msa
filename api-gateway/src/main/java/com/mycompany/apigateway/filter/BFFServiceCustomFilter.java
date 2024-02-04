package com.mycompany.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class BFFServiceCustomFilter extends AbstractGatewayFilterFactory<BFFServiceCustomFilter.Config> {

    private StopWatch stopwatch;

    public BFFServiceCustomFilter() {
        super(Config.class);
        stopwatch = new StopWatch("API Gateway");
    }

    public static class Config{}

    @Override
    public GatewayFilter apply(Config config) {

        return (((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            stopwatch.start();
            log.info("BFFServiceCustomFilter Request -> IP : {}, URI : {}", request.getRemoteAddress().getAddress(), request.getURI());
            request.getHeaders().forEach((key, value) -> {
                log.info("[요청 Header] {} : {}", key, value);
            });

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                stopwatch.stop();

                log.info("--------------------------------------");
                response.getHeaders().forEach((key, value) -> {
                    log.info("[응답 Header] {} : {}", key, value);
                });

                log.info("BFFServiceCustomFilter Response -> IP : {}, URI : {}, 응답코드 : {} -> 처리시간 : {} ms",
                        request.getRemoteAddress().getAddress(),
                        request.getURI(),
                        response.getStatusCode(),
                        stopwatch.getLastTaskTimeMillis()
                );
            }));
        }));
    }

}