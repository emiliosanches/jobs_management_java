package br.com.emiliosanches.jobs_management;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Jobs Offers", description = "A REST api for managing jobs offers and candidates", version = "1.0.0"))
@SecurityScheme(name = "jwt_auth", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class JobsManagementApplication {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		SpringApplication.run(JobsManagementApplication.class, args);
	}

}
