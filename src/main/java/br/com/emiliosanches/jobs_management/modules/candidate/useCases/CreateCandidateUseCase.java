package br.com.emiliosanches.jobs_management.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.emiliosanches.jobs_management.exceptions.UserAlreadyExistsException;
import br.com.emiliosanches.jobs_management.modules.candidate.CandidateEntity;
import br.com.emiliosanches.jobs_management.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public CandidateEntity execute(CandidateEntity candidateEntity) {
    this.candidateRepository
        .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail()).ifPresent((user) -> {
          throw new UserAlreadyExistsException("There's already an user with the provided username or e-mail");
        });

    var password = this.passwordEncoder.encode(candidateEntity.getPassword());
    candidateEntity.setPassword(password);

    return this.candidateRepository.save(candidateEntity);
  }
}
