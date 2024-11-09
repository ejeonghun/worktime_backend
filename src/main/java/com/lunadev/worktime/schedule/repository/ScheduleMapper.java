package com.lunadev.worktime.schedule.repository;

import com.lunadev.worktime.schedule.dto.ScheduleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    /**
     * 특정 회사의 년/월에 해당하는 스케줄 리스트를 조회
     * @param date 년/월 (예: "2024-11")
     * @param companyId 회사 ID
     * @return 스케줄 정보 리스트
     */
    List<ScheduleDto> getScheduleList(@Param("date") String date, @Param("companyId") Long companyId);
}