package br.com.emiliosanches.jobs_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.emiliosanches.jobs_management.exceptions.UserAlreadyExistsException;
import br.com.emiliosanches.jobs_management.modules.company.entity.CompanyEntity;
import br.com.emiliosanches.jobs_management.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {
  @Autowired CompanyRepository companyRepository;

  public CompanyEntity execute(CompanyEntity companyEntity) {
    this.companyRepository.findByEmail(companyEntity.getEmail()).ifPresent((company) -> {
      throw new UserAlreadyExistsException("There's already a company with the provided e-mail");
    });

    return this.companyRepository.save(companyEntity);
  }
}
