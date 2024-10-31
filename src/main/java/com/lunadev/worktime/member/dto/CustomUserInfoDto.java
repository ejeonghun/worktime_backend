package com.lunadev.worktime.member.dto;

import com.lunadev.worktime.Enum.RoleType;
import com.lunadev.worktime.company.entity.Company;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomUserInfoDto extends MemberDto{
    private Long memberId;

    private String email;

    private String name;

    private String password;

    private Company company;

    private String position;

    private RoleType role;

}
