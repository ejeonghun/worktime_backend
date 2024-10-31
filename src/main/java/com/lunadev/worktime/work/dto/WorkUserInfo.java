package com.lunadev.worktime.work.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkUserInfo {
    private Long memberId;
    private String memberName;
    private LocalDateTime startTime;
    private String workType;
    private boolean checkedIn;
}
