package com.lunadev.worktime.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String scheduleType;
    private String scheduleDetails;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endDate;
}
