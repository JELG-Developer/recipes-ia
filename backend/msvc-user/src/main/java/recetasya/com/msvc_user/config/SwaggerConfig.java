package recetasya.com.msvc_user.config;

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
                                .title("Microservice User")
                                .description("Microservice for user management")
                                .version("1.0"));
    }
}