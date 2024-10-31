package com.lunadev.worktime.company.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeptJoinResponse {

    private Long deptId;

    private String deptName;

    private String position;
}
