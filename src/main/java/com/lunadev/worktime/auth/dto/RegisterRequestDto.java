package com.lunadev.worktime.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "AUTH_REQ_02 : 회원가입 요청 DTO")
public class RegisterRequestDto {
    private String email;      // Email
    private String password;   // 비밀번호
    private String name;       // 이름
    private String verifyCode; // 회사코드
}
