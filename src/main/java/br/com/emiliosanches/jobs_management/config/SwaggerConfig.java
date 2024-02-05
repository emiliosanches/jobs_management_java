package br.com.emiliosanches.jobs_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
  private SecurityScheme createSecurityScheme() {
    return new SecurityScheme().name("auth_jwt").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
  };

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info().title("Jobs Management").description("A REST api for managing jobs offers and candidates")
            .version("1.0"))
        .schemaRequirement("jwt_auth", this.createSecurityScheme());
  }
}
