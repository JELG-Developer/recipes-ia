package recetasya.com.msvc_gateway_server.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("msvc-auth-docs", r -> r.path("/v3/api-docs/msvc-auth")
                        .filters(f -> f.rewritePath("/v3/api-docs/msvc-auth", "/v3/api-docs"))
                        .uri("lb://msvc-auth"))
                .route("msvc-user-docs", r -> r.path("/v3/api-docs/msvc-user")
                        .filters(f -> f.rewritePath("/v3/api-docs/msvc-user", "/v3/api-docs"))
                        .uri("lb://msvc-user"))
                .build();
    }
}