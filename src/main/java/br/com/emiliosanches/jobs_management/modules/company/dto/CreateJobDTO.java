package br.com.emiliosanches.jobs_management.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

@Data
public class CreateJobDTO {
  @Schema(example = "Vaga para Desenvolvedor JavaScript", requiredMode = RequiredMode.REQUIRED)
  private String description;

  @Schema(example = "GymPass, plano odontológico, vale transporte", requiredMode = RequiredMode.REQUIRED)
  private String benefits;

  @Schema(example = "Jr", requiredMode = RequiredMode.REQUIRED)
  private String level;
}
