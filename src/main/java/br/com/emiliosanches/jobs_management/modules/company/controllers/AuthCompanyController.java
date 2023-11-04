package br.com.emiliosanches.jobs_management.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.emiliosanches.jobs_management.modules.company.dto.AuthCompanyDTO;
import br.com.emiliosanches.jobs_management.modules.company.useCases.AuthCompanyUseCase;

@RestController
@RequestMapping("/companies/auth")
public class AuthCompanyController {
  @Autowired
  AuthCompanyUseCase authCompanyUseCase;

  @PostMapping({ "/", "" })
  public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO) {
    try {
      return ResponseEntity.ok().body(this.authCompanyUseCase.execute(authCompanyDTO));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
