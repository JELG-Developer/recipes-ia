package recetasya.com.msvc_auth.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Hidden
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI getOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(
                        new Info()
                                .title("Microservice Auth")
                                .description("Microservice for authentication and token issuance")
                                .version("1.0"));
    }
}