package com.lunadev.worktime.company.repository;

import com.lunadev.worktime.company.entity.Company;
import com.lunadev.worktime.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    // 인증코드 쿼리 메서드
    Company findCompanyByVerifyCode(String verifyCode);
}
