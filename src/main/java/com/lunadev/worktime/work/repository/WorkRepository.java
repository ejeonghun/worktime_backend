package com.lunadev.worktime.work.repository;

import com.lunadev.worktime.company.entity.Company;
import com.lunadev.worktime.member.entity.Member;
import com.lunadev.worktime.work.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {
    Work findByMemberAndDate(Member member, LocalDate date);

    List<Work> findByCompanyAndDate(Company company, LocalDate date);
}
