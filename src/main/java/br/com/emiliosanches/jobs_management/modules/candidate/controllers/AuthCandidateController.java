package br.com.emiliosanches.jobs_management.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.emiliosanches.jobs_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.emiliosanches.jobs_management.modules.candidate.useCases.AuthCandidateUseCase;

@RestController
@RequestMapping("/candidates/auth")
public class AuthCandidateController {
  @Autowired
  AuthCandidateUseCase authCandidateUseCase;

  @PostMapping
  public ResponseEntity<Object> auth(@RequestBody AuthCandidateDTO authCandidateDTO) {
    try {
      var result = this.authCandidateUseCase.execute(authCandidateDTO);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
