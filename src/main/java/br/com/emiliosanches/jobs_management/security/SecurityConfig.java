package br.com.emiliosanches.jobs_management.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
  @Autowired
  CompanySecurityFilter companySecurityFilter;

  @Autowired
  CandidateSecurityFilter candidateSecurityFilter;

  private static final String[] SWAGGER_ROUTES = {
      "/swagger-ui/**",
      "/v3/api-docs/**",
      "/swagger/resources/**"
  };

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> {
      auth.requestMatchers("/candidates/", "/candidates").permitAll()
          .requestMatchers("/companies/", "/companies").permitAll()
          .requestMatchers("/companies/auth/", "/companies/auth").permitAll()
          .requestMatchers("/candidates/auth/", "/candidates/auth").permitAll()
          .requestMatchers(SWAGGER_ROUTES).permitAll()
          .anyRequest().authenticated();
    })
        .addFilterBefore(companySecurityFilter, BasicAuthenticationFilter.class)
        .addFilterBefore(candidateSecurityFilter, BasicAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
