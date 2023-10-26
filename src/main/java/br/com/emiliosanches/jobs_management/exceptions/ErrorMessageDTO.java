package br.com.emiliosanches.jobs_management.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageDTO {
  private String message;
  private String field;
}
