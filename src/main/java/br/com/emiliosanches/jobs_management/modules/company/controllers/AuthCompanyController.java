package br.com.emiliosanches.jobs_management.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.emiliosanches.jobs_management.modules.company.dto.AuthCompanyDTO;
import br.com.emiliosanches.jobs_management.modules.company.dto.AuthCompanyResponseDTO;
import br.com.emiliosanches.jobs_management.modules.company.useCases.AuthCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/companies/auth")
@Tag(name = "Companies Authentication")
public class AuthCompanyController {
  @Autowired
  AuthCompanyUseCase authCompanyUseCase;

  @PostMapping
  @Operation(summary = "Authentication as company", description = "This function is responsible for authenticating an user as a company")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AuthCompanyResponseDTO.class))),
      @ApiResponse(responseCode = "401", description = "Incorrect e-mail or password")
  })
  public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO) {
    try {
      return ResponseEntity.ok().body(this.authCompanyUseCase.execute(authCompanyDTO));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
