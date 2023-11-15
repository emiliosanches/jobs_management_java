package br.com.emiliosanches.jobs_management;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Jobs Offers",
		description = "A REST api for managing jobs offers and candidates",
		version = "1.0.0"
	)
)
public class JobsManagementApplication {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		SpringApplication.run(JobsManagementApplication.class, args);
	}

}
