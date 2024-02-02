package br.com.emiliosanches.jobs_management.modules.candidate.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.emiliosanches.jobs_management.modules.company.entity.JobEntity;
import br.com.emiliosanches.jobs_management.modules.company.repositories.JobRepository;

@Service
public class ListJobsByFilterUseCase {
  @Autowired
  JobRepository jobRepository;

  public List<JobEntity> execute(String query) {
    return this.jobRepository.findByDescriptionContainsIgnoreCase(query);
  }
}
