package com.lunadev.worktime.member.dto;

import com.lunadev.worktime.Enum.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailDto {

    private Long memberId;
    private String email;
    private String name;
    private RoleType role;
    private String position;
    private Long deptId;
    private String deptName;
    private String imagePath;
    private Long workTime;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

}
