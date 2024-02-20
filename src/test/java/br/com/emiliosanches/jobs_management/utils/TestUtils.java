package br.com.emiliosanches.jobs_management.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
  public static String objectToJson(Object obj) {
    try {
      final ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String generateToken(UUID subject, String secret) {
    Algorithm algorithm = Algorithm.HMAC256(secret);

    var expires_at = Instant.now().plus(Duration.ofHours(2));

    var token = JWT.create()
        .withIssuer("MAIN_SERVICE")
        .withSubject(subject.toString())
        .withExpiresAt(expires_at)
        .withClaim("roles", Arrays.asList("COMPANY"))
        .sign(algorithm);

    return token;
  }
}
