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

    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.department LEFT JOIN FETCH m.works w WHERE m.company = :company AND (w.date = :date OR w.date IS NULL)")
    List<Member> findMembersWithDepartmentAndWorkByCompanyAndDate(@Param("company") Company company, @Param("date") LocalDate date);

}
