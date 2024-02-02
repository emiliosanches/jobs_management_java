package br.com.emiliosanches.jobs_management.modules.company.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.emiliosanches.jobs_management.modules.company.entity.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
  public List<JobEntity> findByDescriptionContains(String filter);
}
