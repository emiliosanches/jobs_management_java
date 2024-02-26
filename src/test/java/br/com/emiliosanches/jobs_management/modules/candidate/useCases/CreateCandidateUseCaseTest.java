package br.com.emiliosanches.jobs_management.modules.candidate.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;
import java.util.UUID;

import br.com.emiliosanches.jobs_management.exceptions.UserAlreadyExistsException;
import br.com.emiliosanches.jobs_management.modules.candidate.CandidateEntity;
import br.com.emiliosanches.jobs_management.modules.candidate.CandidateRepository;

@ExtendWith(MockitoExtension.class)
class CreateCandidateUseCaseTest {
  @InjectMocks
  private CreateCandidateUseCase createCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("Should not be able to create candidate if username already exists")
  public void shouldNotBeAbleCreateACandidateIfUsernameAlreadyExists() {
    var candidateEntity = CandidateEntity.builder().name("User teste").username("username_test").email("test@email.com")
        .password("PasswordTest123").build();

    when(candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail()))
        .thenReturn(Optional.of(new CandidateEntity()));

    try {
      this.createCandidateUseCase.execute(candidateEntity);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(UserAlreadyExistsException.class);
    }
  }

  @Test
  public void shouldBeAbleToCreateACandidate() {
    var candidateEntity = CandidateEntity.builder().name("User teste").username("username_test").email("test@email.com")
        .password("PasswordTest123").build();

    var candidateSaved = CandidateEntity.builder().id(UUID.randomUUID()).build();

    when(candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail()))
        .thenReturn(Optional.empty());
    when(candidateRepository.save(candidateEntity)).thenReturn(candidateSaved);

    var result = this.createCandidateUseCase.execute(candidateEntity);

    assertThat(result).hasFieldOrProperty("id");
    assertNotNull(result);
  }
}
