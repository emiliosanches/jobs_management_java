package br.com.emiliosanches.jobs_management.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.emiliosanches.jobs_management.exceptions.UserNotFoundException;
import br.com.emiliosanches.jobs_management.modules.candidate.CandidateRepository;
import br.com.emiliosanches.jobs_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.emiliosanches.jobs_management.modules.candidate.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateUseCase {
  @Autowired
  CandidateRepository candidateRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Value("${security.token.secret.candidate}")
  private String secretKey;

  public AuthCandidateResponseDTO execute(AuthCandidateDTO authCandidateDTO) throws AuthenticationException {
    var candidate = this.candidateRepository.findByUsername(authCandidateDTO.username())
        .orElseThrow(() -> new UserNotFoundException());

    boolean passwordMatches = this.passwordEncoder.matches(authCandidateDTO.password(), candidate.getPassword());

    if (!passwordMatches)
      throw new AuthenticationException();

    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    var expires_at = Instant.now().plus(Duration.ofHours(2));

    var token = JWT.create()
        .withIssuer("MAIN_SERVICE")
        .withSubject(candidate.getId().toString())
        .withClaim("roles", Arrays.asList("CANDIDATE"))
        .withExpiresAt(expires_at)
        .sign(algorithm);

    return AuthCandidateResponseDTO.builder().access_token(token).expires_at(expires_at.toEpochMilli()).build();
  }
}
