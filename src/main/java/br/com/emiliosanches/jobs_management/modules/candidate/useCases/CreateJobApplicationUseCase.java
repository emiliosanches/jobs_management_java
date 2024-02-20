package br.com.emiliosanches.jobs_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.emiliosanches.jobs_management.exceptions.JobNotFoundException;
import br.com.emiliosanches.jobs_management.exceptions.UserNotFoundException;
import br.com.emiliosanches.jobs_management.modules.candidate.CandidateRepository;
import br.com.emiliosanches.jobs_management.modules.candidate.entity.JobApplicationEntity;
import br.com.emiliosanches.jobs_management.modules.candidate.repository.JobApplicationRepository;
import br.com.emiliosanches.jobs_management.modules.company.repositories.JobRepository;

public class CreateJobApplicationUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private JobApplicationRepository jobApplicationRepository;

  public JobApplicationEntity execute(UUID candidateId, UUID jobId) {
    this.candidateRepository.findById(candidateId).orElseThrow(() -> new UserNotFoundException());

    this.jobRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException());

    var jobApplication = JobApplicationEntity.builder().candidateId(candidateId).jobId(jobId).build();

    jobApplication = this.jobApplicationRepository.save(jobApplication);

    return jobApplication;
  }
}
