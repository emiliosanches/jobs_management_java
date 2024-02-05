package br.com.emiliosanches.jobs_management.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record AuthCandidateDTO(
  @Schema(example = "john_d0e", requiredMode = RequiredMode.REQUIRED, description = "Candidate username to attempt authentication")
  String username,
  @Schema(example = "p4ssw0rd", requiredMode = RequiredMode.REQUIRED, description = "Candidate password to attempt authentication")
  String password
) {
}