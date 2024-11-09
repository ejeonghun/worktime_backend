package com.lunadev.worktime.schedule.dto;

import com.lunadev.worktime.Enum.ScheduleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    private Long scheduleId;
    private String scheduleName;
    private ScheduleType scheduleType;
    private String scheduleDetails;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Long memberId;
    private Long companyId;
    private Long deptId;

    private String memberName;
    private String deptName;
}