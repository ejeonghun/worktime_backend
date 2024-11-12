package com.lunadev.worktime.member.repository;

import com.lunadev.worktime.member.dto.MemberDetailDto;
import com.lunadev.worktime.work.dto.WorkListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    /**
     * 해당 회원의 자세한 정보를 조회하는 메소드 (예 : 오늘 근무시간, 출근 시간 등)
     * @param memberId 해당 회원 Id
     * @return 근태 정보 리스트, 회원 정보 리스트
     */
    MemberDetailDto getMemberDetail(@Param("memberId") Long memberId);
}
