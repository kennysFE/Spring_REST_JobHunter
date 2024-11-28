package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.service.CompanyService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<?> createCompany(@Valid @RequestBody Company reqCompany) {

        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.handleCreateCompany(reqCompany));

    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompany() {

        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.fetchAllCompanies());

    }

    @GetMapping("/companies/{companyId}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("companyId") long companyId) {

        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.fetchCompanyById(companyId));

    }

    @PutMapping("companies")
    public ResponseEntity<Company> updateCompany(@RequestBody Company reqCompany) {

        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.handleUpdateCompany(reqCompany));

    }

    @DeleteMapping("/companies/{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("companyId") long companyId) {

        this.companyService.handleDeleteCompany(companyId);

        return ResponseEntity.status(HttpStatus.OK).body(null);

    }

}
