package com.mycompany.apigateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (config.isPreLogInfo()) {
                log.info("GolbalFilter Request -> IP : {}, URI : {}", request.getRemoteAddress().getAddress(), request.getURI());
                log.info("GolbalFilter Request -> ID : {}, PATH : {}", request.getId(), request.getPath());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogInfo()) {
                    log.info("GolbalFilter Response -> IP : {}, URI : {}, 응답코드 : {}",
                        request.getRemoteAddress().getAddress(),
                        request.getURI(),
                        response.getStatusCode()
                    );
                }
            }));
        }));
    }

    @Data
    public static class Config {
        private boolean preLogInfo;
        private boolean postLogInfo;
    }
}
