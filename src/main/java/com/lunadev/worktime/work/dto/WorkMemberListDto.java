package com.lunadev.worktime.work.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkMemberListDto {
    private Long memberId;
    private String memberName;
    private String workType;
}