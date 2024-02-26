package br.com.emiliosanches.jobs_management.modules.candidate.useCases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import javax.naming.AuthenticationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.emiliosanches.jobs_management.exceptions.UserNotFoundException;
import br.com.emiliosanches.jobs_management.modules.candidate.CandidateEntity;
import br.com.emiliosanches.jobs_management.modules.candidate.CandidateRepository;
import br.com.emiliosanches.jobs_management.modules.candidate.dto.AuthCandidateDTO;

@ExtendWith(MockitoExtension.class)
class AuthCandidateUseCaseTest {
  @InjectMocks
  private AuthCandidateUseCase authCandidateUseCase;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private CandidateRepository candidateRepository;

  @Test
  public void shouldBeAbleToAuthenticateAsCandidate() throws AuthenticationException {
    var authCandidateDTO = new AuthCandidateDTO("username", "password");

    var candidateEntity = CandidateEntity.builder().id(UUID.randomUUID()).build();

    when(candidateRepository.findByUsername(authCandidateDTO.username()))
        .thenReturn(Optional.of(candidateEntity));

    when(passwordEncoder.matches(authCandidateDTO.password(), candidateEntity.getPassword())).thenReturn(true);

    ReflectionTestUtils.setField(authCandidateUseCase, "secretKey", "secret");

    var result = this.authCandidateUseCase.execute(authCandidateDTO);

    assertNotNull(result.getAccess_token());
  }

  @Test
  public void shouldNotBeAbleToAuthenticateAsCandidateWithWrongPassword() {
    var authCandidateDTO = new AuthCandidateDTO("username", "password");

    var candidateEntity = CandidateEntity.builder().id(UUID.randomUUID()).build();

    when(candidateRepository.findByUsername(authCandidateDTO.username()))
        .thenReturn(Optional.of(candidateEntity));

    when(passwordEncoder.matches(authCandidateDTO.password(), candidateEntity.getPassword())).thenReturn(false);

    ReflectionTestUtils.setField(authCandidateUseCase, "secretKey", "secret");

    assertThrows(AuthenticationException.class, () -> this.authCandidateUseCase.execute(authCandidateDTO));
  }

  @Test
  public void shouldNotBeAbleToAuthenticateAsCandidateWithWrongUsername() {
    var authCandidateDTO = new AuthCandidateDTO("username", "password");


    when(candidateRepository.findByUsername(authCandidateDTO.username()))
        .thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> this.authCandidateUseCase.execute(authCandidateDTO));
  }
}
