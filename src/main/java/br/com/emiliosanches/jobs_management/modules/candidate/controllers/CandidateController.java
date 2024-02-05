package br.com.emiliosanches.jobs_management.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.emiliosanches.jobs_management.exceptions.UserAlreadyExistsException;
import br.com.emiliosanches.jobs_management.modules.candidate.CandidateEntity;
import br.com.emiliosanches.jobs_management.modules.candidate.dto.CandidateProfileDTO;
import br.com.emiliosanches.jobs_management.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.emiliosanches.jobs_management.modules.candidate.useCases.GetCandidateProfileUseCase;
import br.com.emiliosanches.jobs_management.modules.candidate.useCases.ListJobsByFilterUseCase;
import br.com.emiliosanches.jobs_management.modules.company.entity.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidates")
public class CandidateController {
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private GetCandidateProfileUseCase getCandidateProfileUseCase;

  @Autowired
  private ListJobsByFilterUseCase listJobsByFilterUseCase;

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
  @Tag(name = "Candidate", description = "Candidate information")
  @Operation(summary = "Candidate profile", description = "This function is responsible for showing candidate profile information")
  @ApiResponses({ @ApiResponse(responseCode = "200", content = {
      @Content(schema = @Schema(implementation = CandidateProfileDTO.class))
  }) })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> getProfile(HttpServletRequest request) {
    try {
      var candidateId = request.getAttribute("candidateId");

      var profile = this.getCandidateProfileUseCase.execute(UUID.fromString(candidateId.toString()));

      return ResponseEntity.ok().body(profile);
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/jobs")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Tag(name = "Candidate", description = "Candidate information")
  @Operation(summary = "Jobs listing for the candidate", description = "This function is responsible for listing all the jobs based on the filter")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
      })
  })
  @SecurityRequirement(name = "jwt_auth")
  public List<JobEntity> listByFilter(@RequestParam(required = false, defaultValue = "") String filter) {
    return this.listJobsByFilterUseCase.execute(filter);
  }
}
