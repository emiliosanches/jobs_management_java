package br.com.emiliosanches.jobs_management.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.emiliosanches.jobs_management.modules.company.dto.AuthCompanyDTO;
import br.com.emiliosanches.jobs_management.modules.company.entity.CompanyEntity;
import br.com.emiliosanches.jobs_management.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {
  @Value("${security.token.secret.company}")
  private String secretKey;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
    CompanyEntity company = this.companyRepository.findByEmail(authCompanyDTO.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("Company not found"));

    boolean passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

    if (!passwordMatches)
      throw new AuthenticationException();

    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    return JWT.create()
        .withIssuer("MAIN_SERVICE")
        .withSubject(company.getId().toString())
        .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
        .sign(algorithm);
  }
}
