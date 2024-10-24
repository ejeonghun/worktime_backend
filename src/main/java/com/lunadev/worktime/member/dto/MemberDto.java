package com.lunadev.worktime.member.dto;

import com.lunadev.worktime.Enum.RoleType;
import jakarta.persistence.*;

import java.util.List;

public class MemberDto {

    private Long memberId;
    private String email;
    private String name;
    private String password;
    private RoleType role;
}
