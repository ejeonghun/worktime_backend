package com.lunadev.worktime.member.repository;

import com.lunadev.worktime.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 쿼리 메서드
    Member findMemberByEmail(String email);

}
