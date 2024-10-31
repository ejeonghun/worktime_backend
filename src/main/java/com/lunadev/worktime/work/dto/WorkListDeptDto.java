package com.lunadev.worktime.work.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkListDeptDto {
    private String deptName;
    private List<WorkMemberDto> memberList; // 부서에 속한 사원들의 출근 정보 리스트
}