package vn.hoidanit.jobhunter.service;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CompanyRepository;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }

    // Get all company with pagination, filter, sort
    public ResultPaginationDTO fetchAllCompanies(Specification<Company> spec, Pageable pageable) {
        Page<Company> pageCompany = this.companyRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        // default spring application check pageNumber = 0 => to display true pageNumber
        // => set pageNumber add 1
        mt.setPage(pageable.getPageNumber() + 1);

        mt.setPageSize(pageable.getPageSize());
        mt.setPages(pageCompany.getTotalPages());
        mt.setTotal(pageCompany.getNumberOfElements());

        rs.setMeta(mt);
        rs.setResult(pageCompany.getContent());

        return rs;

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
        Optional<Company> comOptional = this.companyRepository.findById(companyId);
        if (comOptional.isPresent()) {
            Company com = comOptional.get();
            // fetch all user belong to this company
            List<User> users = this.userRepository.findByCompany(com);

            // delete user has id company same as current company
            this.userRepository.deleteAll(users);
        }
        this.companyRepository.deleteById(companyId);
    }

    public boolean handleCheckCompanyExists(long companyId) {
        return this.companyRepository.existsById(companyId);
    }

    public Optional<Company> findById(long id) {
        return this.companyRepository.findById(id);
    }

}
