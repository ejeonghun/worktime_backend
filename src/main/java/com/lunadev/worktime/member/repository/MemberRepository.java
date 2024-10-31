package com.lunadev.worktime.member.repository;

import com.lunadev.worktime.company.entity.Company;
import com.lunadev.worktime.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 쿼리 메서드
    Member findMemberByEmail(String email);

    List<Member> findByCompany(Company company);
}
