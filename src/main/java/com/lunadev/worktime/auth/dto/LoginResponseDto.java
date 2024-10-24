package com.lunadev.worktime.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "AUTH_RSP_02 : 로그인 응답 DTO")
public class LoginResponseDto {

    private String token;

    private HttpStatus status;
    private String message;
}
