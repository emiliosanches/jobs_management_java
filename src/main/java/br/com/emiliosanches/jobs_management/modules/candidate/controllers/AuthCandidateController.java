package br.com.emiliosanches.jobs_management.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.emiliosanches.jobs_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.emiliosanches.jobs_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.emiliosanches.jobs_management.modules.candidate.useCases.AuthCandidateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/candidates/auth")
@Tag(name = "Candidates Authentication")
public class AuthCandidateController {
  @Autowired
  AuthCandidateUseCase authCandidateUseCase;

  @PostMapping
  @Operation(summary = "Authentication as candidate", description = "This function is responsible for authenticating an user as a candidate")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AuthCandidateResponseDTO.class))),
      @ApiResponse(responseCode = "401", description = "Incorrect username or password")
  })
  public ResponseEntity<Object> auth(@RequestBody AuthCandidateDTO authCandidateDTO) {
    try {
      var result = this.authCandidateUseCase.execute(authCandidateDTO);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
