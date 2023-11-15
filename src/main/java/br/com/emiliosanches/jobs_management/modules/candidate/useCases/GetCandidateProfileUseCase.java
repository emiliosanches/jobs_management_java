package br.com.emiliosanches.jobs_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.emiliosanches.jobs_management.modules.candidate.CandidateRepository;
import br.com.emiliosanches.jobs_management.modules.candidate.dto.CandidateProfileDTO;

@Service
public class GetCandidateProfileUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  public CandidateProfileDTO execute(UUID candidateId) {
    var candidate = this.candidateRepository.findById(candidateId)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return CandidateProfileDTO.builder()
        .email(candidate.getEmail())
        .name(candidate.getName())
        .description(candidate.getDescription())
        .username(candidate.getUsername())
        .id(candidate.getId()).build();
  }
}
