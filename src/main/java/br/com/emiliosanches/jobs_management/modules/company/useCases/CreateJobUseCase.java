package br.com.emiliosanches.jobs_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.emiliosanches.jobs_management.modules.company.entity.JobEntity;
import br.com.emiliosanches.jobs_management.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {
  @Autowired JobRepository jobRepository;

  public JobEntity execute(JobEntity jobEntity) {
    return this.jobRepository.save(jobEntity);
  }
}
