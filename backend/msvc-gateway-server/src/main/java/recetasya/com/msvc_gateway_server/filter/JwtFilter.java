package recetasya.com.msvc_gateway_server.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter {

    public Mono<ServerWebExchange> filter(ServerWebExchange exchange) {
        return ReactiveSecurityContextHolder.getContext()
            .map(context -> context.getAuthentication())
            .defaultIfEmpty(null)
            .flatMap(auth -> {
                if (auth != null) {
                    String token = extractToken(auth);
                    if (token != null) {
                        ServerHttpRequest request = exchange.getRequest()
                            .mutate()
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                            .build();
                        return Mono.just(exchange.mutate().request(request).build());
                    }
                }
                return Mono.just(exchange);
            });
    }

    private String extractToken(Authentication authentication) {
        return authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;
    }
}
