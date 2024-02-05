package br.com.emiliosanches.jobs_management.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.emiliosanches.jobs_management.exceptions.UserAlreadyExistsException;
import br.com.emiliosanches.jobs_management.modules.company.entity.CompanyEntity;
import br.com.emiliosanches.jobs_management.modules.company.useCases.CreateCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/companies")
@Tag(name = "Companies", description = "Companies information")
public class CompanyController {
  @Autowired
  private CreateCompanyUseCase createCompanyUseCase;

  @PostMapping
  @Operation(summary = "Company registration", description = "This function is responsible for registering a company")
  @ApiResponses({
      @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = CompanyEntity.class))),
      @ApiResponse(responseCode = "401", description = "Company already exists")
  })
  public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.createCompanyUseCase.execute(companyEntity));
    } catch (UserAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
  }

}
