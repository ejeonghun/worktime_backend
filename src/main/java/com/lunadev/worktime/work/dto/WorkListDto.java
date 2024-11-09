package com.lunadev.worktime.work.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkListDto {
        private Long workId;      // 근태 기록 ID
        private LocalDate date;   // 근태 기록 날짜
        private Long workType;   // 근태 유형
        private Long memberId;     // 회원 ID
        private String memberName; // 회원 이름
        private Long companyId;    // 회사 ID
        private Long deptId;       // 부서 ID
        private String deptName;   // 부서 이름
        private String position;   // 직급
        private String imagePath;  // 사원 이미지
        private LocalDateTime startTime;
}
