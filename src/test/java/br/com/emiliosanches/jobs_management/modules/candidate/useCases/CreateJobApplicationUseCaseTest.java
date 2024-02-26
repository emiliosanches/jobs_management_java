package br.com.emiliosanches.jobs_management.modules.candidate.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import br.com.emiliosanches.jobs_management.exceptions.JobNotFoundException;
import br.com.emiliosanches.jobs_management.exceptions.UserNotFoundException;
import br.com.emiliosanches.jobs_management.modules.candidate.CandidateEntity;
import br.com.emiliosanches.jobs_management.modules.candidate.CandidateRepository;
import br.com.emiliosanches.jobs_management.modules.candidate.entity.JobApplicationEntity;
import br.com.emiliosanches.jobs_management.modules.candidate.repository.JobApplicationRepository;
import br.com.emiliosanches.jobs_management.modules.company.entity.JobEntity;
import br.com.emiliosanches.jobs_management.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
class CreateJobApplicationUseCaseTest {
	@InjectMocks
	private CreateJobApplicationUseCase applyCandidateToJobUseCase;

	@Mock
	private CandidateRepository candidateRepository;

	@Mock
	private JobRepository jobRepository;

	@Mock
	private JobApplicationRepository jobApplicationRepository;

	@Test
	@DisplayName("Should not be able to apply job with candidate not found")
	public void shouldNotBeAbleToApplyToJobIfCandidateNotFound() {
		try {
			this.applyCandidateToJobUseCase.execute(null, null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(UserNotFoundException.class);
		}
	}

	@Test
	public void shouldNotBeAbleToApplyToJobIfJobNotFound() {
		var candidateId = UUID.randomUUID();
		var candidate = new CandidateEntity();
		candidate.setId(candidateId);

		when(this.candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

		try {
			this.applyCandidateToJobUseCase.execute(candidateId, null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(JobNotFoundException.class);
		}
	}

	@Test
	public void shouldBeAbleToCreateAJobApplication() {
		var candidateId = UUID.randomUUID();
		var jobId = UUID.randomUUID();

		var jobApplication = JobApplicationEntity.builder().candidateId(candidateId).jobId(jobId).build();

		var jobApplicationCreated = JobApplicationEntity.builder().id(UUID.randomUUID()).build();

		when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(new CandidateEntity()));
		when(jobRepository.findById(jobId)).thenReturn(Optional.of(new JobEntity()));
		when(jobApplicationRepository.save(jobApplication)).thenReturn(jobApplicationCreated);

		var result = applyCandidateToJobUseCase.execute(candidateId, jobId);

		assertThat(result).hasFieldOrProperty("id");
		assertNotNull(result.getId());
	}
}
