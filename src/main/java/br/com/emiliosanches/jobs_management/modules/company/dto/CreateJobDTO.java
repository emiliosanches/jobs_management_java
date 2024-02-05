package br.com.emiliosanches.jobs_management.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

@Data
public class CreateJobDTO {
  @Schema(example = "React/NextJS developer", requiredMode = RequiredMode.REQUIRED)
  private String description;

  @Schema(example = "GymPass, health insurance, paid parental leave", requiredMode = RequiredMode.REQUIRED)
  private String benefits;

  @Schema(example = "Mid level", requiredMode = RequiredMode.REQUIRED)
  private String level;
}
