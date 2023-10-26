package br.com.emiliosanches.jobs_management.modules.candidate;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CandidateEntity {
  private UUID id;
  private String name;

  @Pattern(regexp = "^\\S+$", message = "Field [username] should not contain whitespaces")
  private String username;

  @Email(message = "Field [email] must contain a valid e-mail address")
  private String email;

  @Length(min = 10, max = 100, message = "Field [password] should have length between 10 and 100")
  private String password;

  private String description;
  private String curriculum;
}
