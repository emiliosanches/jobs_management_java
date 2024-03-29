package br.com.emiliosanches.jobs_management.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.emiliosanches.jobs_management.exceptions.UserAlreadyExistsException;
import br.com.emiliosanches.jobs_management.exceptions.UserNotFoundException;
import br.com.emiliosanches.jobs_management.modules.candidate.CandidateEntity;
import br.com.emiliosanches.jobs_management.modules.candidate.dto.CandidateProfileDTO;
import br.com.emiliosanches.jobs_management.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.emiliosanches.jobs_management.modules.candidate.useCases.CreateJobApplicationUseCase;
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
@RequestMapping("candidates")
@Tag(name = "Candidates", description = "Candidates information")
public class CandidateController {
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private GetCandidateProfileUseCase getCandidateProfileUseCase;

  @Autowired
  private ListJobsByFilterUseCase listJobsByFilterUseCase;

  @Autowired
  private CreateJobApplicationUseCase createJobApplicationUseCase;

  @PostMapping
  @Operation(summary = "Candidate registration", description = "This function is responsible for registering a candidate")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = CandidateEntity.class))
      }),
      @ApiResponse(responseCode = "400", description = "User already exists")
  })
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      return ResponseEntity.status(201).body(this.createCandidateUseCase.execute(candidateEntity));
    } catch (UserAlreadyExistsException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Candidate profile", description = "This function is responsible for showing candidate profile information")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = CandidateProfileDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "User not found")
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> getProfile(HttpServletRequest request) {
    try {
      var candidateId = request.getAttribute("candidateId");

      var profile = this.getCandidateProfileUseCase.execute(UUID.fromString(candidateId.toString()));

      return ResponseEntity.ok().body(profile);
    } catch (UserNotFoundException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("jobs")
  @PreAuthorize("hasRole('CANDIDATE')")
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

  @PostMapping("application")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Create an application from a candidate to a job", description = "This function is responsible for applying a candidate to a job")
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID jobId) {
    var candidateId = request.getAttribute("candidateId");

    try {
      var result = this.createJobApplicationUseCase.execute(UUID.fromString(candidateId.toString()), jobId);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
