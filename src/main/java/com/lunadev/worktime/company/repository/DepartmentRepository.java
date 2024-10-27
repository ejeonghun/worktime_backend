package com.lunadev.worktime.company.repository;

import com.lunadev.worktime.company.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
