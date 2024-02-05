package br.com.emiliosanches.jobs_management.modules.candidate;

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
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidates")
public class CandidateEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Schema(example = "John Doe", requiredMode = RequiredMode.REQUIRED, description = "Candidate name")
  private String name;

  @Schema(example = "john_d0e", requiredMode = RequiredMode.REQUIRED, description = "Unique username used to identify the candidate")
  @Pattern(regexp = "^\\S+$", message = "Field [username] should not contain whitespaces")
  private String username;

  @Schema(example = "joe_dohn@gmail.com", requiredMode = RequiredMode.REQUIRED, description = "Candidate e-mail for contacts")
  @Email(message = "Field [email] must contain a valid e-mail address")
  private String email;

  @Schema(example = "johndoesverysecurepassword", minLength = 10, maxLength = 100, requiredMode = RequiredMode.REQUIRED, description = "Candidate password")
  @Length(min = 10, max = 100, message = "Field [password] should have length between 10 and 100")
  private String password;

  @Schema(example = "I'm a Java developer, working with back-end development since 2016. Started my career in 2010 as mobile developer.", description = "Candidate professional description")
  private String description;

  private String curriculum;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
