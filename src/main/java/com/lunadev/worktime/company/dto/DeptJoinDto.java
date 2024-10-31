package com.lunadev.worktime.company.dto;

import com.lunadev.worktime.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeptJoinDto {
    private Long deptId;

    private String position;
}
