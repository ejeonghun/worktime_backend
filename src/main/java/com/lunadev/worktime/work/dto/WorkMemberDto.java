package com.lunadev.worktime.work.dto;

import com.lunadev.worktime.Enum.WorkType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkMemberDto {
    private Long memberId;
    private String memberName;
    private String imagePath;
    private WorkType workType;
    private String position;
    private LocalDateTime startTime;
}