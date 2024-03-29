package br.com.emiliosanches.jobs_management.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.emiliosanches.jobs_management.exceptions.CompanyNotFoundException;
import br.com.emiliosanches.jobs_management.modules.company.dto.CreateJobDTO;
import br.com.emiliosanches.jobs_management.modules.company.entity.JobEntity;
import br.com.emiliosanches.jobs_management.modules.company.useCases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/jobs")
@Tag(name = "Jobs", description = "Jobs information")
public class JobController {
  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping
  @PreAuthorize("hasRole('COMPANY')")
  @Operation(summary = "Job creation", description = "This function responsible for creating a job related to the authenticated company")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = JobEntity.class))
      })
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
    var companyId = request.getAttribute("companyId");

    try {
      var jobEntity = JobEntity.builder()
          .benefits(createJobDTO.getBenefits())
          .description(createJobDTO.getDescription())
          .level(createJobDTO.getLevel())
          .companyId(UUID.fromString(companyId.toString()))
          .build();

      var result = this.createJobUseCase.execute(jobEntity);

      return ResponseEntity.ok().body(result);
    } catch (CompanyNotFoundException e) {
      return ResponseEntity.badRequest().body(e);
    }
  }
}
