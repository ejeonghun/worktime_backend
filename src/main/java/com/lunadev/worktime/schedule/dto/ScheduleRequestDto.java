package com.lunadev.worktime.schedule.dto;

import com.lunadev.worktime.Enum.ScheduleType;
import com.lunadev.worktime.company.entity.Company;
import com.lunadev.worktime.company.entity.Department;
import com.lunadev.worktime.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequestDto {

    private Long scheduleId;
    private String scheduleName;
    private ScheduleType scheduleType;
    private String scheduleDetails;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Member member;
    private Company company;
    private Department department;
}
