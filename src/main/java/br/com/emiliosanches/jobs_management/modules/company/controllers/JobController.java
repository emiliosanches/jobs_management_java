package br.com.emiliosanches.jobs_management.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.emiliosanches.jobs_management.modules.company.dto.CreateJobDTO;
import br.com.emiliosanches.jobs_management.modules.company.entity.JobEntity;
import br.com.emiliosanches.jobs_management.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/jobs")
public class JobController {
  @Autowired private CreateJobUseCase createJobUseCase;

  @PostMapping({ "/", "" })
  public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
    var companyId = request.getAttribute("companyId");

  
    var jobEntity = JobEntity.builder()
      .benefits(createJobDTO.getBenefits())
      .description(createJobDTO.getDescription())
      .level(createJobDTO.getLevel())
      .companyId(UUID.fromString(companyId.toString()))
      .build();

    return this.createJobUseCase.execute(jobEntity);
  }
}
