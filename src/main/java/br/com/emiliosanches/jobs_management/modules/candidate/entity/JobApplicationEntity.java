package br.com.emiliosanches.jobs_management.modules.candidate.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import br.com.emiliosanches.jobs_management.modules.candidate.CandidateEntity;
import br.com.emiliosanches.jobs_management.modules.company.entity.JobEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "apply_jobs")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobApplicationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "candidate_id")
  private UUID candidateId;

  @Column(name = "job_id")
  private UUID jobId;

  @ManyToOne
  @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
  private CandidateEntity candidateEntity;

  @ManyToOne
  @JoinColumn(name = "job_id", insertable = false, updatable = false)
  private JobEntity jobEntity;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
