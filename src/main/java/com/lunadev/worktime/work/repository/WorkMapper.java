package com.lunadev.worktime.work.repository;

import com.lunadev.worktime.work.dto.WorkListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkMapper {

    /**
     * 해당 회사의 특정 날짜에 대한 근태 정보를 조회하는 메소드
     * @param date 기준 날짜
     * @param companyId 회사 ID
     * @return 근태 정보 리스트
     */
    List<WorkListDto> getWorkList(@Param("date") String date, @Param("companyId") Long companyId);
}