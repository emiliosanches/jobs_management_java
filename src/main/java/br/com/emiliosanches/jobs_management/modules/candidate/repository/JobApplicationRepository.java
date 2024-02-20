package br.com.emiliosanches.jobs_management.modules.candidate.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.emiliosanches.jobs_management.modules.candidate.entity.JobApplicationEntity;

public interface JobApplicationRepository extends JpaRepository<JobApplicationEntity, UUID> {

}
