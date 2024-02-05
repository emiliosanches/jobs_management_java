package br.com.emiliosanches.jobs_management.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyDTO {
  @Schema(example = "jobs@company.com", requiredMode = RequiredMode.REQUIRED, description = "Company e-mail to attempt authentication")
  private String email;

  @Schema(example = "companyPasswd1", requiredMode = RequiredMode.REQUIRED, description = "Company password to attempt authentication")
  private String password;
}
