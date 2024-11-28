package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.repository.CompanyRepository;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }

    // Get all company without pagination
    public List<Company> fetchAllCompanies() {
        return this.companyRepository.findAll();
    }

    public Company fetchCompanyById(long companyId) {
        Optional<Company> currentCompany = this.companyRepository.findById(companyId);
        if (currentCompany.isPresent()) {
            return currentCompany.get();
        }
        return null;
    }

    public Company handleUpdateCompany(Company reqCompany) {
        Company currentCompany = this.fetchCompanyById(reqCompany.getId());
        if (currentCompany != null) {
            currentCompany.setName(reqCompany.getName());
            currentCompany.setDescription(reqCompany.getDescription());
            currentCompany.setAddress(reqCompany.getAddress());
            currentCompany.setLogo(reqCompany.getLogo());

            return this.companyRepository.save(currentCompany);
        }
        return currentCompany;
    }

    public void handleDeleteCompany(long companyId) {
        this.companyRepository.deleteById(companyId);
    }

}
