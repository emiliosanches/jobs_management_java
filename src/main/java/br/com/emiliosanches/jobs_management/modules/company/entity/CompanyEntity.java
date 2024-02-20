package br.com.emiliosanches.jobs_management.modules.company.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "companies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Schema(example = "Company Inc.", requiredMode = RequiredMode.REQUIRED, description = "Company name")
  private String name;

  @Schema(example = "jobs@company.com", requiredMode = RequiredMode.REQUIRED, description = "Company e-mail for authentication and contacts")
  @Email(message = "Field [email] must contain a valid e-mail address")
  private String email;

  @Schema(example = "c0mp4ny_p#ss", minLength = 10, maxLength = 100, requiredMode = RequiredMode.REQUIRED, description = "Company password")
  @Length(min = 10, max = 100, message = "Field [password] should have length between 10 and 100")
  private String password;

  @Schema(example = "www.company.com", requiredMode = RequiredMode.NOT_REQUIRED, description = "Company website")
  private String website;

  @Schema(example = "A technology company focused on developing innovative solutions for the market.", requiredMode = RequiredMode.REQUIRED, description = "Description of the company's activities, culture, etc.")
  private String description;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
