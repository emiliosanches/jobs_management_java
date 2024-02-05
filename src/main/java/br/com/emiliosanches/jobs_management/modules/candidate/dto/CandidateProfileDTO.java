package br.com.emiliosanches.jobs_management.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateProfileDTO {
  @Schema(example = "John Doe")
  private String name;

  @Schema(example = "I'm a Java developer, working with back-end development since 2016. Started my career in 2010 as mobile developer.")
  private String description;

  @Schema(example = "john_d0e")
  private String username;

  @Schema(example = "joe_dohn@gmail.com")
  private String email;

  private UUID id;
}
