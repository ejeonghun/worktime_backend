package com.lunadev.worktime.member.dto;

import com.lunadev.worktime.Enum.RoleType;
import com.lunadev.worktime.company.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoDto {

    private Long memberId;
    private String email;
    private String name;
    private RoleType role;
    private String position;
    private Long deptId;
}
