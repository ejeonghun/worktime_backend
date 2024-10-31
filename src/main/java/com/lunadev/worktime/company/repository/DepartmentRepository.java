package com.lunadev.worktime.company.repository;

import com.lunadev.worktime.company.entity.Company;
import com.lunadev.worktime.company.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByCompanyAndDeptName(Company company, String deptName);

    ArrayList<Department> findAllByCompany(Company company);
}
