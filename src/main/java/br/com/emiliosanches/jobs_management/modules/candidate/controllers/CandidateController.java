package br.com.emiliosanches.jobs_management.modules.candidate.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.emiliosanches.jobs_management.exceptions.UserAlreadyExistsException;
import br.com.emiliosanches.jobs_management.modules.candidate.CandidateEntity;
import br.com.emiliosanches.jobs_management.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.emiliosanches.jobs_management.modules.candidate.useCases.GetCandidateProfileUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidates")
public class CandidateController {
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private GetCandidateProfileUseCase getCandidateProfileUseCase;

  @PostMapping
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      return ResponseEntity.status(201).body(this.createCandidateUseCase.execute(candidateEntity));
    } catch (UserAlreadyExistsException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping
  @PreAuthorize("hasRole('CANDIDATE')")
  public ResponseEntity<Object> getProfile(HttpServletRequest request) {
    try {
      var candidateId = request.getAttribute("candidateId");

      var profile = this.getCandidateProfileUseCase.execute(UUID.fromString(candidateId.toString()));

      return ResponseEntity.ok().body(profile);
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
